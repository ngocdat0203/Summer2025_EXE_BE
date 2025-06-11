package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.config.VNPayConfig;
import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.entity.AppointmentAssignment;
import com.example.lovenhavestopsystem.repository.IAppointmentRepository;
import com.example.lovenhavestopsystem.service.inter.IPaymentService;
import com.example.lovenhavestopsystem.service.inter.IVNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class VNPayService implements IVNPayService {
    private final VNPayConfig vnpayConfig;
    private final IAppointmentRepository appointmentRepository;
    private final IPaymentService paymentService;

    @Autowired
    public VNPayService(VNPayConfig vnpayConfig, IAppointmentRepository appointmentRepository, IPaymentService paymentService) {
        this.vnpayConfig = vnpayConfig;
        this.appointmentRepository = appointmentRepository;
        this.paymentService = paymentService;
    }

    @Override
    public String pay(int appointmentId, String returnUrl, HttpServletRequest request) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isEmpty()) {
            throw new NotFoundException("appointment not found");
        }

        Appointment appointment = optionalAppointment.get();
        com.example.lovenhavestopsystem.model.entity.Service service = appointment.getService();
        AppointmentAssignment assignment = appointment.getAppointmentAssignment();

        if (assignment == null || assignment.getStartTime() == null || assignment.getEndTime() == null) {
            throw new IllegalStateException("Appointment assignment or its time is not set");
        }

        long minutes = Duration.between(assignment.getStartTime(), assignment.getEndTime()).toMinutes();
        int hours = (int) Math.ceil(minutes / 60.0);
        int pricePerHour = service.getPricePerHour().intValue();
        int totalPrice = pricePerHour * hours;

        int deposit = pricePerHour / 2;
        int remainingAmount = totalPrice - deposit;
        String userEmail = appointment.getCustomer().getEmail();

        String transactionCode = generateTransactionCode();

        paymentService.createPayment(
                appointmentId,
                remainingAmount,
                "ATM",
                transactionCode,
                "PENDING",
                "REMAINING",
                userEmail,
                "LoveHavenStopSystem",
                "Pay the remainder of the appointment #" + appointmentId
        );

        return createPaymentUrl(request, appointmentId, remainingAmount, returnUrl, transactionCode);
    }


    @Override
    public String deposit(int appointmentId, String returnUrl, HttpServletRequest request) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isEmpty()) {
            throw new NotFoundException("appointment not found");
        }

        int price = appointment.get().getService().getPricePerHour().intValue();
        int depositAmount = price / 2;
        String transactionCode = generateTransactionCode();
        String userEmail = appointment.get().getCustomer().getEmail();

        paymentService.createPayment(
                appointmentId,
                depositAmount,
                "ATM",
                transactionCode,
                "PENDING",
                "DEPOSIT",
                userEmail,
                "LoveHavenStopSystem",
                "Deposit for appointment #" + appointmentId
        );

        return createPaymentUrl(request, appointmentId, depositAmount, returnUrl, transactionCode);
    }


    private String createPaymentUrl(HttpServletRequest request, int appointmentId, int amount, String returnUrl, String transactionCode) {
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", vnpayConfig.getVersion());
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount", String.valueOf(amount * 100L));
        vnpParams.put("vnp_OrderInfo", String.valueOf(appointmentId));
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_IpAddr", getClientIp(request));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        vnpParams.put("vnp_CreateDate", now.format(formatter));
        vnpParams.put("vnp_ExpireDate", now.plusMinutes(10).format(formatter));

        vnpParams.put("vnp_TxnRef", transactionCode);

        String hashData = buildQueryString(vnpParams, false);
        String secureHash = hmacSHA512(vnpayConfig.getSecretKey(), hashData);
        vnpParams.put("vnp_SecureHash", secureHash);

        return vnpayConfig.getPayUrl() + "?" + buildQueryString(vnpParams, true);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return (ip != null && !ip.isEmpty()) ? ip : "127.0.0.1";
    }

    private String generateTransactionCode() {
        return System.currentTimeMillis() + String.format("%03d", new Random().nextInt(999));
    }

    private static String buildQueryString(Map<String, String> params, boolean encode) {
        return params.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> (encode ? URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII) : entry.getKey()) + "=" +
                        URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII))
                .collect(Collectors.joining("&"));
    }

    private static String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKeySpec);
            byte[] hashBytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create HMAC-SHA512", e);
        }
    }


}
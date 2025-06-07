package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.dto.request.PaymentCreateDTO;
import com.example.lovenhavestopsystem.dto.response.PaymentResponseDTO;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.entity.Payment;
import com.example.lovenhavestopsystem.repository.IAppointmentRepository;
import com.example.lovenhavestopsystem.repository.IPaymentRepository;
import com.example.lovenhavestopsystem.service.inter.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {
    private final IPaymentRepository paymentRepository;
    private final IAppointmentRepository iAppointmentRepository;

    @Autowired
    public PaymentService(IPaymentRepository paymentRepository,
                          IAppointmentRepository iAppointmentRepository) {
        this.paymentRepository = paymentRepository;
        this.iAppointmentRepository = iAppointmentRepository;
    }


    @Override
    public void createPayment(PaymentCreateDTO paymentCreateDTO) {
        Payment payment = new Payment();
        payment.setAmount(paymentCreateDTO.getAmount());
        payment.setTransactionCode(paymentCreateDTO.getTransactionCode());
        payment.setMethod(paymentCreateDTO.getMethod());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        payment.setPaidAt(LocalDateTime.parse(paymentCreateDTO.getPaidAt(), formatter));
        if (paymentCreateDTO.getResponseCode().equals("00")) {
            payment.setStatus("Success");
        } else {
            payment.setStatus("Failed");
        }
        Appointment appointment = iAppointmentRepository
                .findByIdAndDeletedTimeIsNull(paymentCreateDTO.getAppointmentId());
        if (appointment == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }
        payment.setAppointment(appointment);
        paymentRepository.save(payment);

    }

    @Override
    public List<PaymentResponseDTO> getLatestPayments() {
        List<Payment> payments = paymentRepository.findTop7ByOrderByCreatedTimeDesc();
        List<PaymentResponseDTO> paymentResponseDTOS = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
            paymentResponseDTO.setAmount(payment.getAmount());
            paymentResponseDTO.setMethod(payment.getMethod());
            paymentResponseDTO.setTransactionCode(payment.getTransactionCode());
            paymentResponseDTO.setPaidAt(payment.getPaidAt());
            paymentResponseDTO.setStatus(payment.getStatus());
            paymentResponseDTO.setAppointmentId(payment.getAppointment().getId());
            paymentResponseDTO.setServiceName(payment.getAppointment().getService().getName());
            paymentResponseDTO.setCustomerEmail(payment.getAppointment().getCustomer().getEmail());
            paymentResponseDTOS.add(paymentResponseDTO);
        }
        return paymentResponseDTOS;
    }
}

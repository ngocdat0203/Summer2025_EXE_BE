package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.entity.Payment;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import com.example.lovenhavestopsystem.repository.IAppointmentRepository;
import com.example.lovenhavestopsystem.repository.IPaymentRepository;
import com.example.lovenhavestopsystem.service.inter.IAppointmentService;
import com.example.lovenhavestopsystem.service.inter.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
    private final IPaymentRepository paymentRepository;
    private final IAppointmentRepository appointmentRepository;
    private final IAppointmentService appointmentService;

    @Autowired
    public PaymentService(IPaymentRepository paymentRepository,
                          IAppointmentRepository appointmentRepository, IAppointmentService appointmentService) {
        this.paymentRepository = paymentRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
    }

    @Override
    public void createPayment(double amount,
                              String method,
                              String transactionCode,
                              String status,
                              String type,
                              String from,
                              String to,
                              String description) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setTransactionCode(transactionCode);
        payment.setStatus(status);
        payment.setType(type);
        payment.setFromAccount(from);
        payment.setToAccount(to);
        payment.setDescription(description);
        paymentRepository.save(payment);
    }

    @Override
    public void updateStatus(String transactionCode, String status) {
//        Payment payment = paymentRepository.findByTransactionCode(transactionCode);
//        if (payment == null) {
//            throw new NotFoundException("Payment not found");
//        }
//        int appointmentId = payment.getAppointment().getId();
//        if(status.equalsIgnoreCase("Success")) {
//            if(payment.getType().equalsIgnoreCase("DEPOSIT")){
//                appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.DEPOSITED);
//            }
//            if(payment.getType().equalsIgnoreCase("REMAINING")){
//                appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.PAID);
//            }
//        }
//        payment.setStatus(status);
//        paymentRepository.save(payment);
    }

    @Override
    public double getAllIncome() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .filter(payment -> "Success".equalsIgnoreCase(payment.getStatus()) && "Pay".equalsIgnoreCase(payment.getType()))
                .mapToDouble(Payment::getAmount)
                .sum();
    }
   @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();

    }

    @Override
    public List<Payment> getByConsultantIdAndMonth(String email, int month, int year) {
        List<Payment> payments = paymentRepository.findAll();
        if (payments.isEmpty()) {
            throw new NotFoundException("No payments found");
        }
        return payments.stream()
                .filter(payment -> payment.getToAccount().equals(email))
                .filter(payment -> payment.getCreatedTime().getMonthValue() == month && payment.getCreatedTime().getYear() == year)
                .filter(payment -> "Success".equalsIgnoreCase(payment.getStatus()) && "Pay".equalsIgnoreCase(payment.getType()))
                .toList();
    }

    @Override
    public Double getByAllTotalIncomeInMonth(int month, int year) {
        List<Payment> payments = paymentRepository.findAll();

        if (payments.isEmpty()) {
            throw new NotFoundException("No payments found");
        }

        // Calculate total income for the specified month and year
        double totalIncome = payments.stream()
                .filter(payment -> payment.getCreatedTime().getMonthValue() == month && payment.getCreatedTime().getYear() == year)
                .filter(payment -> "Success".equalsIgnoreCase(payment.getStatus()) && "Pay".equalsIgnoreCase(payment.getType()))
                .mapToDouble(Payment::getAmount)
                .sum();

        return totalIncome;
    }

    @Override
    public List<Payment> getByAllIncomeInMonth(int month, int year) {
        List<Payment> payments = paymentRepository.findAll();

        if (payments.isEmpty()) {
            throw new NotFoundException("No payments found");
        }

        return payments.stream()
                .filter(payment -> payment.getCreatedTime().getMonthValue() == month && payment.getCreatedTime().getYear() == year)
                .filter(payment -> "Success".equalsIgnoreCase(payment.getStatus()) && "Pay".equalsIgnoreCase(payment.getType()))
                .toList();
    }

    @Override
    public List<Payment> getByEmail(String email) {
        List<Payment> payments = paymentRepository.findAll();

        if (payments.isEmpty()) {
            throw new NotFoundException("No payments found");
        }

        return payments.stream()
                .filter(payment -> payment.getFromAccount().equals(email) || payment.getToAccount().equals(email))
                .toList();
    }


}

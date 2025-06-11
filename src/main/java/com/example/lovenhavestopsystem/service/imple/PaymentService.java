package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.entity.Payment;
import com.example.lovenhavestopsystem.repository.IAppointmentRepository;
import com.example.lovenhavestopsystem.repository.IPaymentRepository;
import com.example.lovenhavestopsystem.service.inter.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
    private final IPaymentRepository paymentRepository;
    private final IAppointmentRepository appointmentRepository;

    @Autowired
    public PaymentService(IPaymentRepository paymentRepository,
                          IAppointmentRepository appointmentRepository) {
        this.paymentRepository = paymentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void createPayment(int appointmentId,
                              double amount,
                              String method,
                              String transactionCode,
                              String status,
                              String type,
                              String from,
                              String to,
                              String description) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        Payment payment = new Payment();
        appointment.ifPresent(payment::setAppointment);
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
        Payment payment = paymentRepository.findByTransactionCode(transactionCode);
        if (payment == null) {
            throw new NotFoundException("Payment not found");
        }
        payment.setStatus(status);
        paymentRepository.save(payment);
    }
}

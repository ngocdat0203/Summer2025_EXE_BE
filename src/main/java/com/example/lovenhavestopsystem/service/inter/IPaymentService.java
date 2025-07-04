package com.example.lovenhavestopsystem.service.inter;

import com.example.lovenhavestopsystem.model.entity.Payment;

import java.util.List;

public interface IPaymentService {
    void createPayment(double amount,
                       String method,
                       String transactionCode,
                       String status,
                       String type,
                       String from,
                       String to,
                       String description);

    void updateStatus(String transactionCode, String status);

    double getAllIncome();

    List<Payment> getAllPayments();

}


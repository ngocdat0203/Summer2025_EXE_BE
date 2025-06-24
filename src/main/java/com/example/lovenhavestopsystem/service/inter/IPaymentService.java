package com.example.lovenhavestopsystem.service.inter;

public interface IPaymentService {
    void createPayment(int appointmentId,
                       double amount,
                       String method,
                       String transactionCode,
                       String status,
                       String type,
                       String from,
                       String to,
                       String description);

    void updateStatus(String transactionCode, String status);

    double getAllIncome();
}


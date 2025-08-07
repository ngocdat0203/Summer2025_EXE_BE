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

    List<Payment> getByConsultantIdAndMonth(String email, int month, int year);


    Double getByAllTotalIncomeInMonth(int month, int year);

    List<Payment> getByAllIncomeInMonth(int month, int year);

    List<Payment> getByEmail(String email);

    List<Payment> getByComeInMonth(int month, int year);

    Double getByComeTotalInMonth(int month, int year);


}


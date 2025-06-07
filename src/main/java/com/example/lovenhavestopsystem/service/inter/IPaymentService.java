package com.example.lovenhavestopsystem.service.inter;

import com.example.lovenhavestopsystem.dto.request.PaymentCreateDTO;
import com.example.lovenhavestopsystem.dto.response.PaymentResponseDTO;

import java.util.List;

public interface IPaymentService {
    void createPayment(PaymentCreateDTO paymentCreateDTO);
    List<PaymentResponseDTO> getLatestPayments();
}


package com.example.lovenhavestopsystem.service.inter;

import jakarta.servlet.http.HttpServletRequest;

public interface IVNPayService {
    String createPaymentUrl(HttpServletRequest request, int appointmentId, int amount, String returnUrl);
}
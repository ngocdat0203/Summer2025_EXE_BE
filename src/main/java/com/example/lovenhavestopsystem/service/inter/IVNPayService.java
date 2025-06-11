package com.example.lovenhavestopsystem.service.inter;

import jakarta.servlet.http.HttpServletRequest;

public interface IVNPayService {
    String pay(int appointmentId, String returnUrl, HttpServletRequest request);
    String deposit(int appointmentId, String returnUrl, HttpServletRequest request);
}
package com.example.lovenhavestopsystem.user.crud.service.inter;

public interface IOtpService {
    String generateOtp(String email);
    boolean verifyOtp(String email, String otp);
    boolean isOtpVerified(String email);
}

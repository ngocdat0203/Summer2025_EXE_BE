package com.example.lovenhavestopsystem.user.crud.service.inter;

import java.util.Map;

public interface IEmailService {
    boolean sendMail(String to, String subject, String body);
    void sendOtpEmail(String email, String otp);
    void sendMailWithThymeleafTemplate(String to, String subject, String templateName, Map<String, Object> model);
}

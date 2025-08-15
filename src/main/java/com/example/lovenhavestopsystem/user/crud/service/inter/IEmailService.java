package com.example.lovenhavestopsystem.user.crud.service.inter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public interface IEmailService {
    boolean sendMail(String to, String subject, String body);
    void sendOtpEmail(String email, String otp);
    void sendMailWithThymeleafTemplate(String to, String subject, String templateName, Map<String, Object> model);

    void sendMailBookingSuccess(String email, int bookingId, String consultantName, LocalDate date, LocalTime time);

    void sendMailKhamSuccess(String email,
                             int bookingId,
                             String consultantName,
                             LocalDate date,
                             LocalTime startTime,
                             LocalTime endTime,
                             int remainingAmount) ;

}

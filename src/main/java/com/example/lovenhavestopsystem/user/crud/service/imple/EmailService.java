package com.example.lovenhavestopsystem.user.crud.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.model.entity.AppointmentAssignment;
import com.example.lovenhavestopsystem.repository.IAppointmentAssignmentRepo;
import com.example.lovenhavestopsystem.user.crud.service.inter.IEmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.context.Context;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * Gửi email đơn giản
     *
     * @param to      Địa chỉ email người nhận
     * @param subject Tiêu đề email
     * @param body    Nội dung email
     * @return true nếu gửi thành công, false nếu có lỗi xảy ra
     */
    @Autowired
    private IAppointmentAssignmentRepo appointmentAssignmentRepo;


    public boolean sendMail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            this.mailSender.send(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void sendOtpEmail(String email, String otp) {
        String subject = BaseMessage.OTP_SUBJECT;

        Map<String, Object> model = new HashMap<>();
        model.put("otp", otp);

        // Gửi mail với template Thymeleaf
        sendMailWithThymeleafTemplate(email, subject, "sucess-email", model);
    }

    public void sendMailWithThymeleafTemplate(String to, String subject, String templateName, Map<String, Object> model) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(model);

            String htmlBody = templateEngine.process(templateName, thymeleafContext);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi
        }
    }

    @Override
    public void sendMailBookingSuccess(String email, int bookingId, String consultantName, LocalDate date, LocalTime time) {

        String subject = "Booking Confirmation - " + bookingId;
        Map<String, Object> model = new HashMap<>();
        model.put("bookingId", bookingId);
        model.put("consultantName", consultantName);
        model.put("date", date);
        model.put("time", time);

        // Gửi mail với template Thymeleaf
        sendMailWithThymeleafTemplate(email, subject, "booking-success-email", model);
    }

}


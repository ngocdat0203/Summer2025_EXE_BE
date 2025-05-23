package com.example.lovenhavestopsystem.user.crud.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.user.crud.service.inter.IEmailService;
import com.example.lovenhavestopsystem.user.crud.service.inter.IOtpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {
    private final IOtpService otpService;
    private final IEmailService emailService;

    public OtpController(IOtpService otpService, IEmailService emailService) {
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<BaseResponse<Void>> sendOtp(@RequestParam String email) {
        String otp = otpService.generateOtp(email);
        emailService.sendOtpEmail(email, otp);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.SEND_SUCCESS));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<BaseResponse<Void>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        otpService.verifyOtp(email, otp);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.VERIFY_SUCCESS));
    }
}
package com.example.lovenhavestopsystem.user.crud.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.user.crud.service.imple.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("send-email")
    public ResponseEntity<String> sendEmail(
            @RequestBody String templateBody,
            @RequestParam String to,
            @RequestParam String subject) {
        return emailService.sendMail(to,subject,templateBody) ?
                new ResponseEntity<>(
                        BaseMessage.SEND_SUCCESS,
                        HttpStatus.OK
                )
                : new ResponseEntity<>(
                BaseMessage.SEND_FAIL,
                HttpStatus.NOT_IMPLEMENTED
        );
    }
}

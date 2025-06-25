package com.example.lovenhavestopsystem.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.service.inter.IVNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vn-pay")
public class VNPayController {
    private final IVNPayService service;

    public VNPayController(IVNPayService service) {
        this.service = service;
    }

    @GetMapping("/get-vn-pay")
    public ResponseEntity<BaseResponse<?>> getVNPayUrl(@RequestParam int userId, @RequestParam int amount, @RequestParam String returnUrl, HttpServletRequest request) {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, service.createPaymentUrl(request, userId, amount, returnUrl)));
    }

}

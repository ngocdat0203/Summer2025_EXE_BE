package com.example.lovenhavestopsystem.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.entity.Payment;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import com.example.lovenhavestopsystem.service.inter.IPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PutMapping("/update-status-by-transaction-code")
    public ResponseEntity<BaseResponse<Void>> updateAppointmentStatus(@RequestParam String transactionCode,
                                                                      @RequestParam String status) {
        paymentService.updateStatus(transactionCode, status);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.UPDATE_SUCCESS));
    }


    @GetMapping("/get-all-income")
    public ResponseEntity<BaseResponse<?>> getAllIncome(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, paymentService.getAllIncome()));

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<Payment>>> getAll() {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, paymentService.getAllPayments()));

    }
}

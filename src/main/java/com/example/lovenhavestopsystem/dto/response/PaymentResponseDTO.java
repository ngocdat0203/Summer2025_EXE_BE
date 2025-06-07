package com.example.lovenhavestopsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private double amount;
    private LocalDateTime paidAt;
    private String transactionCode;
    private String method;
    private String status;
    private int appointmentId;
    private String serviceName;
    private String customerEmail;
}

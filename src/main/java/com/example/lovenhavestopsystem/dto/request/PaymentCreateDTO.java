package com.example.lovenhavestopsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateDTO {
    private int appointmentId;
    private double amount;
    private String transactionCode;
    private String method;
    private String paidAt;
    private String responseCode;
}

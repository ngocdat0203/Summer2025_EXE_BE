package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    @JsonManagedReference
    private Appointment appointment;

    private Double amount;

    private LocalDateTime paidAt;

    private String method;

    private String transactionCode;

    private String status; //Failed or Success

    private String type; //Pay, Refund, Salary

    private String fromAccount;

    private String toAccount;

    private String description;
}

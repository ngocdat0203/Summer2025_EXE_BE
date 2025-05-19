package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commission extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Account consultant;

    private LocalDate date;

    private Double totalAmount;

    private Double commission;

    private LocalDateTime submittedAt;

    private boolean isLate;
}

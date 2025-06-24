package com.example.lovenhavestopsystem.user.crud.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Wallet extends BaseEntity {

    private double balance = 0.0;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account user;
}

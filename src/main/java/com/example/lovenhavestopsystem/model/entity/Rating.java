package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating extends BaseEntity {
    @OneToOne
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Account customer;

    @ManyToOne
    @JoinColumn(name = "consultantProfiles_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private ConsultantProfiles consultant;

    private int stars;

    private String comment;

    private LocalDateTime createdAt;
}

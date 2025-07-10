package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentAssignment extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "consultantProfiles_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private ConsultantProfiles consultant;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime acceptedAt;

    private boolean isPaid;

}

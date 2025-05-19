package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.parser.AcceptEncoding;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Account customer;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Service service;

    private String address;

    private String city;

    private LocalDateTime preferredTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status; // PENDING, TAKEN, DONE, CANCELED

    @OneToOne(mappedBy = "appointment")
    @JsonBackReference
    private AppointmentAssignment appointmentAssignment;

    @OneToMany(mappedBy = "appointment",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Payment> payments;

}

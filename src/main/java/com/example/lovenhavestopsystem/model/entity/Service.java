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

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service extends BaseEntity {
    @Column(columnDefinition = "NVARCHAR(355)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(355)")
    private String description;

    private Double pricePerHour;

    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Appointment> appointments;


    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ServiceConsultant> serviceConsultants;

    private String imageUrl;

    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Image> images;


}

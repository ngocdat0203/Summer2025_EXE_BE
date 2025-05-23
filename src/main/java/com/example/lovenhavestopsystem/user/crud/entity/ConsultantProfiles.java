package com.example.lovenhavestopsystem.user.crud.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.model.entity.Image;
import com.example.lovenhavestopsystem.model.entity.ServiceConsultant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantProfiles extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonManagedReference
    private Account account;

    private String bio;
    private String expertise;
    private LocalDateTime approvedAt;
    private Double rating;


    @OneToMany(mappedBy = "consultantProfiles",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ServiceConsultant> serviceConsultants;

    @OneToMany(mappedBy = "consultantProfiles",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Image> images;
}

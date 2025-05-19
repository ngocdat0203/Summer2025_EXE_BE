package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceConsultant extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false ,referencedColumnName = "id")
    @JsonBackReference
    private Service service;

    @ManyToOne
    @JoinColumn(name = "consultantProfiles_id", nullable = false ,referencedColumnName = "id")
    @JsonBackReference
    private ConsultantProfiles consultantProfiles;


}

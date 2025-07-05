package com.example.lovenhavestopsystem.user.crud.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.model.entity.Image;
import com.example.lovenhavestopsystem.model.entity.ServiceConsultant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"account", "serviceConsultants", "images"})
@ToString(callSuper = true, exclude = {"account", "serviceConsultants", "images"})
public class ConsultantProfiles extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonManagedReference
    private Account account;

    private String bio;
    private String expertise;

    @ElementCollection
    @CollectionTable(name = "consultant_certificates", joinColumns = @JoinColumn(name = "consultant_profiles_id"))
    @Column(name = "certificate_url")
    private List<String> urlCertificates;

    private LocalDateTime approvedAt;



    @OneToMany(mappedBy = "consultantProfiles",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ServiceConsultant> serviceConsultants;

    @OneToMany(mappedBy = "consultantProfiles",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Image> images;
}

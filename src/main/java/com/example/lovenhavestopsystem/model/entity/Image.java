package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    @JsonBackReference
    private Service service;

    @ManyToOne
    @JoinColumn(name = "consultantProfiles_id", referencedColumnName = "id")
    @JsonBackReference
    private ConsultantProfiles consultantProfiles;

}
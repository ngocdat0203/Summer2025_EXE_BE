package com.example.lovenhavestopsystem.dto.request;

import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class ServiceRequestDTO {
    private String name;
    private String description;
    private Double pricePerHour;
    private List<Integer> consultantProfileIds;
    private String imageUrl;
}

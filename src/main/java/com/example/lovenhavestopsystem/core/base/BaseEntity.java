package com.example.lovenhavestopsystem.core.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    private LocalDateTime createdTime;
    @JsonIgnore
    private LocalDateTime lastUpdatedTime;
    @JsonIgnore
    private LocalDateTime deletedTime;

    @PrePersist
    protected void onCreate() {
        createdTime = lastUpdatedTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedTime = LocalDateTime.now();
    }


}

package com.example.lovenhavestopsystem.core.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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

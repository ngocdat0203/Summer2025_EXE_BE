package com.example.lovenhavestopsystem.user.crud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.example.lovenhavestopsystem.core.base.BaseEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.example.lovenhavestopsystem.user.crud.enums.Status;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@jakarta.persistence.Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private RoleName name;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<Account> accounts;
}

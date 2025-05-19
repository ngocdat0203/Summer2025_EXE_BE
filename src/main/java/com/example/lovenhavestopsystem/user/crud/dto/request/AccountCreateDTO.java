package com.example.lovenhavestopsystem.user.crud.dto.request;

import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountCreateDTO {
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
    private List<RoleName> roles;
    private Integer experience;
    private String status;
}

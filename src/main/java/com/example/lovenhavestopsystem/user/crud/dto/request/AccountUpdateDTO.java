package com.example.lovenhavestopsystem.user.crud.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountUpdateDTO {
    private String password;
    private String name;
    private String phone;
    private String address;
}


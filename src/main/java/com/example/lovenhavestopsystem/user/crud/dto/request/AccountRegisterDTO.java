package com.example.lovenhavestopsystem.user.crud.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegisterDTO {
    private String password;
    private String email;
}

package com.example.lovenhavestopsystem.user.crud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInformationDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
}

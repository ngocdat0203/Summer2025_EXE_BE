package com.example.lovenhavestopsystem.user.crud.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultantProfilesDTO {
    private String bio;
    private String expertise;
    private Double rating;
    private int accountId;
    private List<Integer> imagesID;
    private String password;
    private String name;
    private String phone;
    private String address;
}

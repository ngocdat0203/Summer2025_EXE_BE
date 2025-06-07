package com.example.lovenhavestopsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountDTO {
    private Long id;
    private String name;
    private String email;
    private String urlImage;
}

package com.example.lovenhavestopsystem.user.crud.dto.request;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultantRegisterDTO {
    private AccountRegisterDTO accountRegisterDTO;
    private String bio;
    private String expertise;
    private List<String> certificates; // Comma-separated list of certificate IDs
}

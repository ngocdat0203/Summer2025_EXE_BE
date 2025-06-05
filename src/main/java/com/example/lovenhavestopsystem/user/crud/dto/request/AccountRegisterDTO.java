package com.example.lovenhavestopsystem.user.crud.dto.request;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegisterDTO {
    @NotNull(message = BaseMessage.PASSWORD_NOT_NULL)
    @Size(min = 8, message = BaseMessage.PASSWORD_MIN_LENGTH)
    private String password;
    @NotNull(message = BaseMessage.EMAIL_NOT_NULL)
    @Email(message = BaseMessage.EMAIL_INVALID)
    private String email;
    private String name;
    private String phone;
    private String address;
    private String urlImage;

}

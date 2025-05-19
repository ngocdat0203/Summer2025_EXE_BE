package com.example.lovenhavestopsystem.user.auth.dto;

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
public class LoginDTO {

    @NotNull(message = BaseMessage.EMAIL_NOT_NULL)
    @Email(message = BaseMessage.EMAIL_INVALID)
    private String email;

    @NotNull(message = BaseMessage.PASSWORD_NOT_NULL)
    @Size(min = 8, message = BaseMessage.PASSWORD_MIN_LENGTH)
    /*@Pattern(regexp = ".*[A-Z].*[!@#$%^&*(),.?\":{}|<>].*|.*[!@#$%^&*(),.?\":{}|<>].*[A-Z].*",
            message = Message.PASSWORD_COMPLEXITY)*/
    private String password;
}

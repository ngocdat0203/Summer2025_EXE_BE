package com.example.lovenhavestopsystem.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageRequestDTO {

    @Min(1)
    Integer conversationId;

    @NotBlank(message = "Message cannot be blank")
    String message;
}

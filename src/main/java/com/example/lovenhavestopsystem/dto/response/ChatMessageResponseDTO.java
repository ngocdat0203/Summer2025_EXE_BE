package com.example.lovenhavestopsystem.dto.response;

import com.example.lovenhavestopsystem.user.crud.entity.Account;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageResponseDTO {
    String id;
    String conversationId;
    boolean me;
    String message;
    Account sennder;
    Instant createdDate;
}
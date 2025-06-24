package com.example.lovenhavestopsystem.dto.response;

import com.example.lovenhavestopsystem.model.entity.ParticipantInfo;
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
    ParticipantInfo sennder;
    Instant createdDate;
}

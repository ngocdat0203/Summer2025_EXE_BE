package com.example.lovenhavestopsystem.dto.response;

import com.example.lovenhavestopsystem.user.crud.entity.Account;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationResponseDTO {

    int id;
    String type;
    String participantsHash;
    String conversationAvatar;
    String conversationName;
    List<Account> participants;
    Instant createdAt;
    Instant updatedAt;

}

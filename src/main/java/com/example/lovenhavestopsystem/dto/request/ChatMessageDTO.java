package com.example.lovenhavestopsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private int senderId;
    private int receiverId;
    private String content;
}

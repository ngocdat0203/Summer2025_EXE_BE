package com.example.lovenhavestopsystem.mapper;

import com.example.lovenhavestopsystem.dto.request.ChatMessageRequestDTO;
import com.example.lovenhavestopsystem.dto.response.ChatMessageResponseDTO;
import com.example.lovenhavestopsystem.model.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessageResponseDTO toChatMessageResponse(ChatMessage chatMessage);

    ChatMessage toChatMessage(ChatMessageRequestDTO request);

    List<ChatMessageResponseDTO> toChatMessageResponses(List<ChatMessage> chatMessages);
}

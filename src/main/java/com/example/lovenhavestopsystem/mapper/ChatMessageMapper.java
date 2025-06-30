package com.example.lovenhavestopsystem.mapper;

import com.example.lovenhavestopsystem.dto.request.ChatMessageRequestDTO;
import com.example.lovenhavestopsystem.dto.response.ChatMessageResponseDTO;
import com.example.lovenhavestopsystem.model.entity.ChatMessage;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    @Mapping(source = "chatMessage.id", target = "id")
    @Mapping(source = "chatMessage.message", target = "message")
    @Mapping(source = "chatMessage.createdDate", target = "createdDate")
    @Mapping(source = "chatMessage.conversation.id", target = "conversationId")
    @Mapping(source = "chatMessage.account", target = "sender")
    @Mapping(target = "me", expression = "java(chatMessage.getAccount().getEmail().equalsIgnoreCase(currentUserGmail.trim()))")
    ChatMessageResponseDTO toChatMessageResponse(ChatMessage chatMessage, @Context String currentUserGmail);

    ChatMessage toChatMessage(ChatMessageRequestDTO request);

    List<ChatMessageResponseDTO> toChatMessageResponses(List<ChatMessage> chatMessages, @Context String currentUserGmail);
}

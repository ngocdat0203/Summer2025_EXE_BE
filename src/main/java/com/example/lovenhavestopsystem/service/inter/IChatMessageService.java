package com.example.lovenhavestopsystem.service.inter;

import com.example.lovenhavestopsystem.dto.request.ChatMessageRequestDTO;
import com.example.lovenhavestopsystem.dto.response.ChatMessageResponseDTO;
import com.example.lovenhavestopsystem.model.entity.ChatMessage;

import java.util.List;

public interface IChatMessageService {
/*    ChatMessage sendMessage(String message, int senderId, int receiverId);
    void deleteMessage(int messageId);
    void updateMessage(int messageId, String newContent);
    String getMessageById(int messageId);
    void markMessageAsRead(int messageId, int userId);
    List<ChatMessage> getRecentMessages(int senderId, int receiverId, int page, int size);*/

    List<ChatMessageResponseDTO> getMessageHistory(int conversationId);

    ChatMessageResponseDTO createChatMessage(ChatMessageRequestDTO chatMessageResponseDTO);
}
package com.example.lovenhavestopsystem.service.inter;

import com.example.lovenhavestopsystem.dto.request.ConversationRequestDTO;
import com.example.lovenhavestopsystem.dto.response.ConversationResponseDTO;

public interface IConversationService {

    ConversationResponseDTO createConversation(ConversationRequestDTO conversationRequestDTO);
}
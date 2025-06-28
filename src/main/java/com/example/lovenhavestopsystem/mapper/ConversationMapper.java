package com.example.lovenhavestopsystem.mapper;

import com.example.lovenhavestopsystem.dto.response.ConversationResponseDTO;
import com.example.lovenhavestopsystem.model.entity.Conversation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
    ConversationResponseDTO toConversationResponse(Conversation conversation);

    List<ConversationResponseDTO> toConversationResponseList(List<Conversation> conversations);
}
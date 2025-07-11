package com.example.lovenhavestopsystem.mapper;

import com.example.lovenhavestopsystem.dto.response.FeedbackResponseDTO;
import com.example.lovenhavestopsystem.model.entity.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFeedbackMapper {
    FeedbackResponseDTO toDTO(Feedback feedback);
}

package com.example.lovenhavestopsystem.model.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter(autoApply = true)
public class BlogContentConverter implements AttributeConverter<List<BlogContent>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<BlogContent> benefits) {
        try {
            return objectMapper.writeValueAsString(benefits);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<BlogContent> convertToEntityAttribute(String benefitsJson) {
        try {
            return objectMapper.readValue(benefitsJson, new TypeReference<List<BlogContent>>() {});
        } catch (Exception e) {
            return null;
        }
    }
}


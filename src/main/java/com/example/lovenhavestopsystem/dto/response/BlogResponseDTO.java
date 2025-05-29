package com.example.lovenhavestopsystem.dto.response;

import com.example.lovenhavestopsystem.model.entity.BlogContent;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogResponseDTO {
    private int id;
    private String title;
    private String headline;
    private List<BlogContent> content;
    private String summary;
    private String authorName;
    private LocalDateTime createdTime;
    private String thumbnailUrl;
}


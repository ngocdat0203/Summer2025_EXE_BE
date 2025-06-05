package com.example.lovenhavestopsystem.dto.request;

import com.example.lovenhavestopsystem.model.entity.BlogContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequestDTO {
    private String title;
    private String headline;
    private List<BlogContent> content;
    private String summary;
    private int accountId;
    private String thumbnailUrl;
}

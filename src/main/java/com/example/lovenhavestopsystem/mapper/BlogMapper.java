package com.example.lovenhavestopsystem.mapper;

import com.example.lovenhavestopsystem.dto.request.BlogRequestDTO;
import com.example.lovenhavestopsystem.dto.response.BlogResponseDTO;
import com.example.lovenhavestopsystem.model.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    Blog toEntity(BlogRequestDTO dto);
    @Mapping(source = "account.name", target = "authorName")
    BlogResponseDTO toResponseDTO(Blog blog);
    @Mapping(source = "account.name", target = "authorName")
    List<BlogResponseDTO> toListResponseDTO(List<Blog> blog);
}


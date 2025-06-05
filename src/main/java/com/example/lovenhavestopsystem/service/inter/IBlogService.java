package com.example.lovenhavestopsystem.service.inter;

import com.example.lovenhavestopsystem.dto.request.BlogRequestDTO;
import com.example.lovenhavestopsystem.dto.response.BlogResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IBlogService {
    void createBlog(BlogRequestDTO blogRequestDTO) throws IOException;
    void updateBlog(int blogId, BlogRequestDTO blogRequestDTO) throws IOException;
    void deleteBlog(int blogId);
    BlogResponseDTO getBlogById(int blogId);
    List<BlogResponseDTO> getAll();
    List<BlogResponseDTO> getBlogByAccountId(int accountId);
}

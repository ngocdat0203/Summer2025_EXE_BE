package com.example.lovenhavestopsystem.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.dto.request.BlogRequestDTO;
import com.example.lovenhavestopsystem.service.inter.IBlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final IBlogService blogService;

    @Autowired
    public BlogController(IBlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Void>> create(
            @RequestPart String blog,
            @RequestPart(required = false) MultipartFile image
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BlogRequestDTO blogRequestDTO = objectMapper.readValue(blog, BlogRequestDTO.class);
        blogService.createBlog(blogRequestDTO, image);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.CREATED.value(), BaseMessage.CREATE_SUCCESS));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<Void>> update(
            @PathVariable int id,
            @RequestPart String blog,
            @RequestPart(required = false) MultipartFile image
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BlogRequestDTO blogRequestDTO = objectMapper.readValue(blog, BlogRequestDTO.class);
        blogService.updateBlog(id, blogRequestDTO, image);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.UPDATE_SUCCESS));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.DELETE_SUCCESS));
    }

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<?>> getAll() {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, blogService.getAll()));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<BaseResponse<?>> getById(@PathVariable int id) {
        return ResponseEntity
                .ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, blogService.getBlogById(id)));
    }

    @GetMapping("/getByAccountId/{id}")
    public ResponseEntity<BaseResponse<?>> getByAccountId(@PathVariable int id) {
        return ResponseEntity
                .ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, blogService.getBlogByAccountId(id)));
    }
}

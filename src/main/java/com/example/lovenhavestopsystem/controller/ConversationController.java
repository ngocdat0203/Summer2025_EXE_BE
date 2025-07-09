package com.example.lovenhavestopsystem.controller;


import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.dto.request.ConversationRequestDTO;
import com.example.lovenhavestopsystem.service.imple.ConversationService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    ConversationService conversationService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> createConversation(@RequestBody @Valid ConversationRequestDTO request) {

        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.CREATED.value(), "Conversation created successfully", conversationService.createConversation(request)));

    }

    @GetMapping("/my-conversations")
    public ResponseEntity<BaseResponse<?>> getConversationByAccountJwt() {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), "Conversation retrieved successfully", conversationService.getConversationByAccountJwt()));
    }
}
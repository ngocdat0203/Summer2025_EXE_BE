package com.example.lovenhavestopsystem.controller;


import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.dto.request.ConversationRequestDTO;
import com.example.lovenhavestopsystem.service.imple.ConversationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conversation")
public class ConversationController {

    ConversationService conversationService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> createConversation(@RequestBody @Valid ConversationRequestDTO request) {
        conversationService.createConversation(request);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.CREATED.value(), "Conversation created successfully"));

    }
}

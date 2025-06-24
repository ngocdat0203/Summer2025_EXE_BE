package com.example.lovenhavestopsystem.controller;

import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.dto.request.ChatMessageRequestDTO;
import com.example.lovenhavestopsystem.dto.response.ChatMessageResponseDTO;
import com.example.lovenhavestopsystem.service.imple.ChatMessageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("messages")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatMessageController {
    ChatMessageService chatMessageService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> create(@Valid @RequestBody ChatMessageRequestDTO chatMessageRequestDTO) {
        chatMessageService.createChatMessage(chatMessageRequestDTO);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.CREATED.value(), "Conversation created successfully"));

    }

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<BaseResponse<List<ChatMessageResponseDTO>>> getMessagesByConversationId(@RequestParam int conversationId) {
        List<ChatMessageResponseDTO> chatMessages = chatMessageService.getMessageHistory(conversationId);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), "Messages retrieved successfully", chatMessages));
    }
}
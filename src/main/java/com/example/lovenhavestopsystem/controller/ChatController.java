package com.example.lovenhavestopsystem.controller;

import com.example.lovenhavestopsystem.dto.request.ChatMessageDTO;
import com.example.lovenhavestopsystem.dto.request.ChatMessageRequestDTO;
import com.example.lovenhavestopsystem.dto.response.ChatMessageResponseDTO;
import com.example.lovenhavestopsystem.model.entity.ChatMessage;
import com.example.lovenhavestopsystem.service.imple.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

 /*   private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    public ChatController(SimpMessagingTemplate messagingTemplate,
                          ChatMessageService chatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }*/

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    // Gửi tin nhắn real-time
    @MessageMapping("/send-message")
    public void sendMessage(@Payload ChatMessageRequestDTO messageDTO, Principal principal) {
        System.out.println("===> Received message: " + messageDTO);

        if (SecurityContextHolder.getContext().getAuthentication() == null && principal != null) {
            // Nếu có principal nhưng chưa gán, ta gán lại (nếu cần thiết)
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(principal.getName(), null, List.of())
            );
        }

        ChatMessageResponseDTO savedMessage = chatMessageService.createChatMessage(messageDTO);

        System.out.println("===> Saved message: " + savedMessage);

        // Gửi đến những ai đang theo dõi cuộc trò chuyện
        messagingTemplate.convertAndSend(
                "/topic/conversation/" + messageDTO.getConversationId(),
                savedMessage
        );
    }



  /*  @MessageMapping("/private-message")
    public void sendPrivateMessage(@Payload ChatMessageDTO messageDTO) {
        System.out.println("===> Received message: " + messageDTO);

        ChatMessage savedMessage = chatMessageService.sendMessage(
                messageDTO.getContent(),
                messageDTO.getSenderId(),
                messageDTO.getReceiverId()
        );

        System.out.println("===> Saved message: " + savedMessage);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(messageDTO.getReceiverId()),
                "/queue/messages",
                savedMessage
        );
        System.out.println("===> Sent message to receiverId = " + messageDTO.getReceiverId());

        messagingTemplate.convertAndSendToUser(
                String.valueOf(messageDTO.getSenderId()),
                "/queue/messages",
                savedMessage
        );
        System.out.println("===> Sent message to senderId = " + messageDTO.getSenderId());
    }


    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(
            @RequestParam int senderId,
            @RequestParam int receiverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<ChatMessage> messages = chatMessageService.getRecentMessages(senderId, receiverId, page, size);
        return ResponseEntity.ok(messages);
    }*/

}
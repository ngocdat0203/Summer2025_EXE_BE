package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.base.*;
import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.dto.request.ChatMessageRequestDTO;
import com.example.lovenhavestopsystem.dto.response.ChatMessageResponseDTO;
import com.example.lovenhavestopsystem.mapper.ChatMessageMapper;
import com.example.lovenhavestopsystem.model.entity.ChatMessage;
import com.example.lovenhavestopsystem.model.entity.Conversation;
import com.example.lovenhavestopsystem.repository.IChatMessageRepository;
import com.example.lovenhavestopsystem.repository.IConversationRepository;
import com.example.lovenhavestopsystem.service.inter.IChatMessageService;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ChatMessageService implements IChatMessageService {

    private final IChatMessageRepository chatMessageRepository;
    private final IAccountRepository accountRepository; // để load sender/receiver từ id

    @Autowired
    private IConversationRepository conversationRepository;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    public ChatMessageService(IChatMessageRepository chatMessageRepository, IAccountRepository accountRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.accountRepository = accountRepository;
    }

    /*@Override
    public ChatMessage sendMessage(String content, int senderId, int receiverId) {
        *//*Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Account receiver = accountRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);

        chatMessageRepository.save(message);
        return message;*//*
        return null;
    }

    @Override
    public void deleteMessage(int messageId) {
        if (!chatMessageRepository.existsById(messageId)) {
            throw new RuntimeException("Message not found");
        }
        chatMessageRepository.deleteById(messageId);
    }

    @Override
    public void updateMessage(int messageId, String newContent) {
        *//*ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setContent(newContent);
        chatMessageRepository.save(message);*//*
    }

    @Override
    public String getMessageById(int messageId) {
        *//*ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        return message.getContent();*//*
        return null;
    }

    @Override
    public void markMessageAsRead(int messageId, int userId) {
        *//*ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (message.getReceiver().getId() != (userId)) {
            throw new RuntimeException("Not allowed to mark this message as read");
        }

        message.setRead(true);
        chatMessageRepository.save(message);*//*
    }

    public List<ChatMessage> getRecentMessages(int senderId, int receiverId, int page, int size) {
        *//*Pageable pageable = PageRequest.of(page, size);
        Page<ChatMessage> result = chatMessageRepository.findRecentMessages(senderId, receiverId, pageable);
        return result.getContent();*//*

        return null;
    }
*/
    @Override
    public List<ChatMessageResponseDTO> getMessageHistory(int conversationId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND))
                .getParticipants()
                .stream()
                .filter(participantInfo -> userId.equals(participantInfo.getId()))
                .findAny()
                .orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND));

        var messages = chatMessageRepository.findAllByConversationIdOrderByCreatedDateDesc(conversationId);

        return messages.stream().map(this::toChatMessageResponse).toList();
    }

    /*@Override
    public ChatMessageResponseDTO createChatMessage(ChatMessageRequestDTO chatMessageResponseDTO) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        // Validate conversationId
        conversationRepository.findById(chatMessageResponseDTO.getConversationId())
                .orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND))
                .getParticipants()
                .stream()
                .filter(participantInfo -> userId.equals(participantInfo.getEmail()))
                .findAny()
                .orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND));

        // Get UserInfo from ProfileService
        Account userInfo = accountRepository.findByEmailAndDeletedTimeIsNull(userId);

        // Build Chat message Info
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(chatMessageResponseDTO);
        chatMessage.setSender(Account.builder()
                .userId(userInfo.getId())
                .username(userInfo.getName())
                .avatar(userInfo.getUrlImage())
                .build());
        chatMessage.setCreatedDate(Instant.now());

        // Create chat message
        chatMessage = chatMessageRepository.save(chatMessage);

        // convert to Response
        return toChatMessageResponse(chatMessage);
    }
*/

    @Override
    public ChatMessageResponseDTO createChatMessage(ChatMessageRequestDTO chatMessageResponseDTO) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        // Validate conversationId
        Conversation conversation = conversationRepository.findById(chatMessageResponseDTO.getConversationId())
                .orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND));

        // Check if the current user is a participant
        boolean isParticipant = conversation.getParticipants().stream()
                .anyMatch(account -> userId.equals(account.getEmail()));
        if (!isParticipant) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }

        // Get UserInfo
        Account userInfo = accountRepository.findByEmailAndDeletedTimeIsNull(userId);
        if (userInfo == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }

        // Build Chat message
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(chatMessageResponseDTO);
        chatMessage.setAccount(userInfo); // ✅ fix: dùng entity đã load
        chatMessage.setConversation(conversation); // nếu chưa có dòng này
        chatMessage.setCreatedDate(Instant.now());

        // Save message
        chatMessage = chatMessageRepository.save(chatMessage);

        return toChatMessageResponse(chatMessage);
    }

    private ChatMessageResponseDTO toChatMessageResponse(ChatMessage chatMessage) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var chatMessageResponse = chatMessageMapper.toChatMessageResponse(chatMessage);

        chatMessageResponse.setMe(userName.equals(chatMessage.getAccount().getName()));

        return chatMessageResponse;
    }

}
package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.model.entity.ChatMessage;
import com.example.lovenhavestopsystem.repository.IChatMessageRepository;
import com.example.lovenhavestopsystem.service.inter.IChatMessageService;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService implements IChatMessageService {

    private final IChatMessageRepository chatMessageRepository;
    private final IAccountRepository accountRepository; // để load sender/receiver từ id

    public ChatMessageService(IChatMessageRepository chatMessageRepository, IAccountRepository accountRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public ChatMessage sendMessage(String content, int senderId, int receiverId) {
        Account sender = accountRepository.findById(senderId)
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
        return message;
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
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setContent(newContent);
        chatMessageRepository.save(message);
    }

    @Override
    public String getMessageById(int messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        return message.getContent();
    }

    @Override
    public void markMessageAsRead(int messageId, int userId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (message.getReceiver().getId() != (userId)) {
            throw new RuntimeException("Not allowed to mark this message as read");
        }

        message.setRead(true);
        chatMessageRepository.save(message);
    }

    public List<ChatMessage> getRecentMessages(int senderId, int receiverId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChatMessage> result = chatMessageRepository.findRecentMessages(senderId, receiverId, pageable);
        return result.getContent();
    }

}


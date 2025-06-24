package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.exception.BadRequestException;
import com.example.lovenhavestopsystem.dto.request.ConversationRequestDTO;
import com.example.lovenhavestopsystem.dto.response.ConversationResponseDTO;
import com.example.lovenhavestopsystem.mapper.ConversationMapper;
import com.example.lovenhavestopsystem.model.entity.Conversation;
import com.example.lovenhavestopsystem.model.entity.ParticipantInfo;
import com.example.lovenhavestopsystem.repository.IConversationRepository;
import com.example.lovenhavestopsystem.service.inter.IConversationService;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationService implements IConversationService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IConversationRepository conversationRepository;

    @Autowired
    private ConversationMapper conversationMapper;

    public List<ConversationResponseDTO> myConversations() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Conversation> conversations = conversationRepository.findByAccountName(userId);
        return conversationMapper.toConversationResponseList(conversations);
    }

    @Override
    public ConversationResponseDTO createConversation(ConversationRequestDTO requestDTO) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        var participants = accountRepository.findByNameAndDeletedTimeIsNull(userId);

        var participantInfor = accountRepository.findByNameAndDeletedTimeIsNull(requestDTO.getParticipantId().get(0));

        List<String> userIds = new ArrayList<>();
        userIds.add(String.valueOf(participants.getId()));
        userIds.add(String.valueOf(participantInfor.getId()));

        var sortedIds = userIds.stream()
                .sorted()
                .toList();
        String participantHash = generateParticipantHash(userIds);

        var existingConversation = conversationRepository.findByParticipantHash(participantHash).orElseGet(() -> {
            List<ParticipantInfo> participantsList = List.of(ParticipantInfo.builder()
                            .userId(participants.getId())
                            .username(participants.getEmail())
                            .name(participants.getName())
                            .avatar(participants.getUrlImage())
                            .build(),
                    ParticipantInfo.builder()
                            .userId(participantInfor.getId())
                            .name(participantInfor.getName())
                            .avatar(participantInfor.getUrlImage())
                            .username(participantInfor.getEmail())
                            .build());

            List<Account> accountParticipantsList = (List<Account>) participantsList.stream()
                    .map(participantInfo -> Account.builder()
                            .id(participantInfo.getUserId())
                            .email(participantInfo.getUsername())
                            .name(participantInfo.getName())
                            .urlImage(participantInfo.getAvatar())
                            .build())
                    .toList();

            Conversation conversation = Conversation.builder()
                    .type(requestDTO.getType())
                    .participantHash(participantHash)
                    .participants(accountParticipantsList)
                    .createdAt(Instant.now())
                    .modifiedDate(Instant.now())
                    .build();

            return conversationRepository.save(conversation);
        });
        return toConversationResponse(existingConversation);
    }

    private String generateConversationId() {
        return "conv-" + System.currentTimeMillis();
    }

    private String generateParticipantHash(List<String> ids) {
        StringJoiner joiner = new StringJoiner("-");
        ids.forEach(joiner::add);
        return joiner.toString();
    }

    private ConversationResponseDTO toConversationResponse(Conversation conversation) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        ConversationResponseDTO conversationResponse = conversationMapper.toConversationResponse(conversation);

        conversation.getParticipants().stream()
                .filter(participantInfo -> !String.valueOf(participantInfo.getId()).equals(currentUserId))
                .findFirst().ifPresent(participantInfo -> {
                    conversationResponse.setConversationName(participantInfo.getEmail());
                    conversationResponse.setConversationAvatar(participantInfo.getUrlImage());
                });

        return conversationResponse;
    }
}
package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.exception.BadRequestException;
import com.example.lovenhavestopsystem.dto.request.ConversationRequestDTO;
import com.example.lovenhavestopsystem.dto.response.ConversationResponseDTO;
import com.example.lovenhavestopsystem.mapper.ConversationMapper;
import com.example.lovenhavestopsystem.model.entity.Conversation;
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

    /*@Override
    public ConversationResponseDTO createConversation(ConversationRequestDTO requestDTO) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Account participants = accountRepository.findAccountsByEmail(userId);
        if (participants == null) {
            throw new BadRequestException("Người dùng hiện tại không tồn tại.");
        }


        Account participantInfor = accountRepository.findAccountsByEmail(requestDTO.getParticipantId().get(0));
        if (participantInfor == null) {
            throw new BadRequestException("Người tham gia không tồn tại.");
        }

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

            List<Account> accountParticipantsList = participantsList.stream()
                    .map(participantInfo -> Account.builder()
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
*/
    @Override
    public ConversationResponseDTO createConversation(ConversationRequestDTO requestDTO) {
        // Lấy thông tin người dùng hiện tại từ JWT
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        // Lấy account của người dùng hiện tại
        Account currentUser = accountRepository.findAccountsByEmail(userId);
        if (currentUser == null) {
            throw new BadRequestException("Người dùng hiện tại không tồn tại.");
        }

        // Lấy account của người cần trò chuyện cùng
        String otherUserEmail = requestDTO.getParticipantId().get(0);
        Account otherUser = accountRepository.findAccountsByEmail(otherUserEmail);
        if (otherUser == null) {
            throw new BadRequestException("Người tham gia không tồn tại.");
        }

        // Tạo hash duy nhất cho cuộc hội thoại
        List<String> userIds = List.of(
                String.valueOf(currentUser.getId()),
                String.valueOf(otherUser.getId())
        );
        List<String> sortedIds = userIds.stream().sorted().toList();
        String participantHash = generateParticipantHash(sortedIds); // nhớ dùng sortedIds để nhất quán

        // Kiểm tra cuộc hội thoại đã tồn tại chưa
        var existingConversation = conversationRepository.findByParticipantHash(participantHash).orElseGet(() -> {
            // Tạo cuộc trò chuyện mới nếu chưa có
            Conversation conversation = Conversation.builder()
                    .type(requestDTO.getType())
                    .participantHash(participantHash)
                    .participants(List.of(currentUser, otherUser)) // ✅ Dùng entity đã có trong DB
                    .createdAt(Instant.now())
                    .modifiedDate(Instant.now())
                    .build();

            return conversationRepository.save(conversation);
        });

        // Trả về response DTO
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
    public List<String> getParticipantEmails(int conversationId) {
        Conversation conversation = conversationRepository.findByIdWithParticipants(conversationId)
                .orElseThrow(() -> new BadRequestException("Cuộc trò chuyện không tồn tại."));

        return conversation.getParticipants().stream()
                .map(Account::getEmail)
                .toList();
    }

    public List<ConversationResponseDTO> getConversationByAccountJwt() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Conversation> conversations = conversationRepository.findByAccountEmail(userId);
        if (conversations.isEmpty()) {
            throw new BadRequestException("Không có cuộc trò chuyện nào.");
        }
        return conversationMapper.toConversationResponseList(conversations);
    }
}
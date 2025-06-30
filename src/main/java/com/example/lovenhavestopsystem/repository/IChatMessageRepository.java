package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface IChatMessageRepository extends JpaRepository<ChatMessage, Integer> {/*
    @Query("SELECT cm FROM ChatMessage cm " +
            "WHERE (cm.sender.id = ?1 AND cm.receiver.id = ?2) " +
            "   OR (cm.sender.id = ?2 AND cm.receiver.id = ?1) " +
            "ORDER BY cm.timestamp DESC")
    Page<ChatMessage> findRecentMessages(Integer senderId, Integer receiverId, Pageable pageable);*/


    List<ChatMessage> findAllByConversationIdOrderByCreatedDateDesc(int conversation_id);

    @EntityGraph(attributePaths = {"account", "conversation"})
    Optional<ChatMessage> findChatMessageById(int id);

}
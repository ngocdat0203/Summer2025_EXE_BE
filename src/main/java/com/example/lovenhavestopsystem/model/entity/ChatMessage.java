package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_message")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    @JsonManagedReference
    private Conversation conversation;

    @Column(name = "message", columnDefinition = "LONGTEXT", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonManagedReference
    private Account account;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;
}

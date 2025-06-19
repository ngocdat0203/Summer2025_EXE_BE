package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatMessage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;
    
    @Column(columnDefinition = "NVARCHAR(355)")
    private String content;
    private boolean isRead;
    private LocalDateTime timestamp;
}


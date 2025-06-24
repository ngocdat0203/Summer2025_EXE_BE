package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation extends BaseEntity {

    String type; // "group" or "private"

    @Column(unique = true)
    String participantHash;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    @JsonBackReference
    List<Account> participants;

    Instant createdAt;

    Instant modifiedDate;

    @OneToOne(mappedBy = "conversation", cascade = CascadeType.ALL)
    private ChatMessage lastMessage;
}
package com.example.lovenhavestopsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ParticipantInfo {

    @Id
    int userId;
    String username;
    String name;
    String avatar;


    @OneToMany(mappedBy = "participantInfo",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ChatMessage> messages;


}

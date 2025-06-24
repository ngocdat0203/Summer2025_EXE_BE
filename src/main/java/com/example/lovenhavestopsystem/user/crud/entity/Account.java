package com.example.lovenhavestopsystem.user.crud.entity;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.model.entity.ChatMessage;
import com.example.lovenhavestopsystem.model.entity.Conversation;
import com.example.lovenhavestopsystem.user.crud.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.example.lovenhavestopsystem.core.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    @NotNull(message = BaseMessage.EMAIL_NOT_NULL)
    @Email(message = BaseMessage.EMAIL_INVALID)
    private String email;

    @NotNull(message = BaseMessage.PASSWORD_NOT_NULL)
    @Size(min = 8, message = BaseMessage.PASSWORD_MIN_LENGTH)
    @Pattern(regexp = ".*[A-Z].*[!@#$%^&*(),.?\":{}|<>].*|.*[!@#$%^&*(),.?\":{}|<>].*[A-Z].*",
            message = BaseMessage.PASSWORD_COMPLEXITY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private List<Role> roles;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;

    private String phone;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String address;

    @OneToOne(mappedBy = "account")
    @JsonBackReference
    private ConsultantProfiles consultantProfiles;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String urlImage;

    @OneToMany(mappedBy = "sender")
    private List<ChatMessage> sentMessages;

    @OneToMany(mappedBy = "receiver")
    private List<ChatMessage> receivedMessages;



    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    @JsonManagedReference
    private Conversation conversation;


}
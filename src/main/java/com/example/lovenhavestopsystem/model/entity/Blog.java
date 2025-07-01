package com.example.lovenhavestopsystem.model.entity;

import com.example.lovenhavestopsystem.core.base.BaseEntity;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Blog extends BaseEntity {

    @Column(columnDefinition = "NVARCHAR(355)")
    private String title;
    @Column(columnDefinition = "NVARCHAR(355)")
    private String headline;
    @Column(columnDefinition = "NVARCHAR(355)")
    private String summary;
    private String thumbnailUrl;
    @Column(columnDefinition = "TEXT")
    @Convert(converter = BlogContentConverter.class)
    private List<BlogContent> content;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;
}

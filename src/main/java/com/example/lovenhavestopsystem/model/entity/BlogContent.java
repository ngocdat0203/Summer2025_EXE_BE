package com.example.lovenhavestopsystem.model.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogContent {
    @Column(columnDefinition = "NVARCHAR(355)")
    private String title;
    @Column(columnDefinition = "NVARCHAR(355)")
    private String detail;
}


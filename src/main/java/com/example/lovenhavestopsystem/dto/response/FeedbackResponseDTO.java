package com.example.lovenhavestopsystem.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponseDTO {
    private int id;
    private int consultantId;
    private int customerId;
    private String body;
}

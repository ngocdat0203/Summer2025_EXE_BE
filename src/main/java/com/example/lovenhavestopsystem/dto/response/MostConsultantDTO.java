package com.example.lovenhavestopsystem.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MostConsultantDTO {
    private int consultantId;
    private String consultantName;
    private long usageCount;
}

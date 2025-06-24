package com.example.lovenhavestopsystem.dto.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PopularServiceDTO {

    private int serviceId;
    private String serviceName;
    private long usageCount;
}
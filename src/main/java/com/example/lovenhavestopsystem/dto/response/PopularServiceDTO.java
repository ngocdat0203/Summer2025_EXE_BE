package com.example.lovenhavestopsystem.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularServiceDTO {

    private int serviceId;
    private String serviceName;
    private long usageCount;
}

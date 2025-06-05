package com.example.lovenhavestopsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCreateDTO {
    int accountId;
    int serviceId;
    String address;
    String city;
    LocalDateTime preferredTime;
}

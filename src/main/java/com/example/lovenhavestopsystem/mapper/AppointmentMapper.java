package com.example.lovenhavestopsystem.mapper;

import com.example.lovenhavestopsystem.dto.request.AppointmentCreateDTO;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    Appointment toAppointment(AppointmentCreateDTO appointmentCreateDTO);
}

package com.example.lovenhavestopsystem.service.inter;

import com.example.lovenhavestopsystem.dto.request.AppointmentCreateDTO;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;

import java.util.List;

public interface IAppointmentService {
    int createAppointment(AppointmentCreateDTO dto);
    List<Appointment> getByCustomerId(int id);
    List<Appointment> getByCity(String city);
    void updateAppointmentStatus(int id, AppointmentStatus status);
    List<Appointment> getAllAppointments();
    List<Appointment> getAllAppointmentsByConsultantId(int id);
    Double getTotalAmountByServiceId(int serviceId);
    Double getAllTotalAmount();
    Double getTotalAmountByMonth(int month, int year);
}

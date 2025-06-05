package com.example.lovenhavestopsystem.service.inter;

import com.example.lovenhavestopsystem.model.entity.AppointmentAssignment;

import java.time.LocalDateTime;

public interface IAppointmentAssignmentService {
    void createAppointmentAssignment(int appointmentId, int consultantId);
    void updateAppointmentAssignmentTime(int id, LocalDateTime startTime, LocalDateTime endTime);
    AppointmentAssignment getAssignmentByAppointmentId(int id);
}

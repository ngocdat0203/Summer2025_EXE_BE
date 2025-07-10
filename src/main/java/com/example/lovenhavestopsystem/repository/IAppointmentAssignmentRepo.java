package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.AppointmentAssignment;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IAppointmentAssignmentRepo extends JpaRepository<AppointmentAssignment, Integer> {
    AppointmentAssignment findByAppointment_Id(int id);
    List<AppointmentAssignment> findByAppointment_StatusAndStartTimeBetween(
            AppointmentStatus status,
            LocalDateTime start,
            LocalDateTime end
    );
}

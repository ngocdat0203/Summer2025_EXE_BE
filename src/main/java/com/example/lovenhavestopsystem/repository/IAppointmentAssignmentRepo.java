package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.AppointmentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentAssignmentRepo extends JpaRepository<AppointmentAssignment, Integer> {
    AppointmentAssignment findByAppointment_Id(int id);
}

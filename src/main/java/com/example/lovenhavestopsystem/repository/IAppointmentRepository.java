package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> getAllByCustomer_Id(int customer_id);
    List<Appointment> getAllByCity(String city);
}

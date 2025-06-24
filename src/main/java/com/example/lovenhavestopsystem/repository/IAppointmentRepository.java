package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.dto.response.PopularServiceDTO;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> getAllByCustomer_Id(int customer_id);
    List<Appointment> getAllByCity(String city);
    Appointment findByIdAndDeletedTimeIsNull(int id);

    List<Appointment> findAll();

    @Query("""
    SELECT new com.example.lovenhavestopsystem.dto.response.PopularServiceDTO(
        a.service.id,
        a.service.name,
        COUNT(a)
    )
    FROM Appointment a
    WHERE a.deletedTime IS NULL AND a.appointmentAssignment.isPaid = true
    GROUP BY a.service.id, a.service.name
    ORDER BY COUNT(a) DESC
""")
    List<PopularServiceDTO> findMostPopularService(Pageable pageable);




}

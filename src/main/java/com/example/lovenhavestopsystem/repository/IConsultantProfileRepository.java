package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConsultantProfileRepository extends JpaRepository<ConsultantProfiles, Integer> {

    ConsultantProfiles getById(int id);

    ConsultantProfiles getByIdAndDeletedTimeIsNull(int id);

    Page<ConsultantProfiles> getAllByDeletedTimeIsNull(Pageable pageable);

    @Query("SELECT t FROM ConsultantProfiles t JOIN t.serviceConsultants st WHERE st.service.id = :serviceId")
    List<ConsultantProfiles> getAllByServiceId(int serviceId);

    ConsultantProfiles findByAccountId(int accountId);
}

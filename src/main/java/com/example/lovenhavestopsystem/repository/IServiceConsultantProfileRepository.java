package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.ServiceConsultant;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceConsultantProfileRepository extends JpaRepository<ServiceConsultant, Long> {
}

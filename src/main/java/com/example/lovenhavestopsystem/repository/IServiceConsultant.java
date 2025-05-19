package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.ServiceConsultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceConsultant extends JpaRepository<ServiceConsultant, Integer> {
}

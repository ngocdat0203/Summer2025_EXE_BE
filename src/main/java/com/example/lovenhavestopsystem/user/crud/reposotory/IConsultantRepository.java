package com.example.lovenhavestopsystem.user.crud.reposotory;

import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConsultantRepository extends JpaRepository<ConsultantProfiles, Integer> {
}

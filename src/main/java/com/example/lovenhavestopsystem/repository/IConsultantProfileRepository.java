package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConsultantProfileRepository extends JpaRepository<ConsultantProfiles, Integer> {

    ConsultantProfiles getById(int id);

    ConsultantProfiles getByIdAndDeletedTimeIsNull(int id);
}

package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IServiceRepository extends JpaRepository<Service,Integer> {

    boolean existsByName(String name);

    Service getServicesByIdAndDeletedTimeIsNull(int id);

    boolean existsByNameAndIdNot(String name, int id);

    Page<Service> findAllByDeletedTimeIsNull(Pageable pageable);

    Service findByIdAndDeletedTimeIsNull(int mostPopularServiceId);

}

package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findAll();
    Page<Feedback> findAll(Pageable pageable);
    List<Feedback> getFeedbacksByCustomerId(int customerId, Pageable pageable);
    List<Feedback> getFeedbacksByConsultantId(int consultantId, Pageable pageable);
    }

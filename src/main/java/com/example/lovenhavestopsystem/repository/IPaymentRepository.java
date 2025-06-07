package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findTop7ByOrderByCreatedTimeDesc();
}

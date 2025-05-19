package com.example.lovenhavestopsystem.user.crud.reposotory;

import com.example.lovenhavestopsystem.user.crud.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOtpRepository extends JpaRepository<Otp, Long> {
    Otp findByEmailAndOtp(String email, String otp);

    boolean existsByVerifiedIsTrueAndEmail(String email);
}

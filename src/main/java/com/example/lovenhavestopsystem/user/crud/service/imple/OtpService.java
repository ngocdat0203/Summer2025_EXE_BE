package com.example.lovenhavestopsystem.user.crud.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.exception.BadRequestException;
import com.example.lovenhavestopsystem.user.crud.entity.Otp;
import com.example.lovenhavestopsystem.user.crud.reposotory.IOtpRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {
    private final IOtpRepository otpRepo;
    private final Random random = new Random();

    public OtpService(IOtpRepository otpRepo) {
        this.otpRepo = otpRepo;
    }


    public String generateOtp(String email) {
        String otp = String.format("%06d", random.nextInt(999999));
        Otp otpRecord = new Otp();
        otpRecord.setEmail(email);
        otpRecord.setOtp(otp);
        otpRecord.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        otpRepo.save(otpRecord);
        return otp;
    }


    public boolean verifyOtp(String email, String otp) {
        Otp otpRecord = otpRepo.findByEmailAndOtp(email, otp);
        if (otpRecord == null) {
            throw new BadRequestException(BaseMessage.OTP_INVALID);
        }

        if (otpRecord.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException(BaseMessage.OTP_EXPIRED);
        }
        otpRecord.setVerified(true);
        otpRepo.save(otpRecord);
        return true;
    }


    public boolean isOtpVerified(String email) {
        return otpRepo.existsByVerifiedIsTrueAndEmail(email);
    }
}

package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.model.entity.AppointmentAssignment;
import com.example.lovenhavestopsystem.model.entity.Payment;
import com.example.lovenhavestopsystem.model.entity.Wallet;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import com.example.lovenhavestopsystem.repository.IAppointmentAssignmentRepo;
import com.example.lovenhavestopsystem.repository.IPaymentRepository;
import com.example.lovenhavestopsystem.repository.IWalletRepository;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryService {

    private final IAppointmentAssignmentRepo appointmentAssignmentRepository;
    private final IWalletRepository walletRepository;
    private final IPaymentRepository paymentRepository;

    /**
     * Lịch chạy tự động trả lương
     * Chạy lúc 2h sáng ngày 5 hàng tháng
     */
    @Scheduled(cron = "0 0 2 5 * *")
    @Transactional
    public void processMonthlySalary() {
        log.info("=== START Monthly Salary Job ===");

        LocalDateTime now = LocalDateTime.now();

        // Tính khoảng thời gian của tháng trước
        LocalDateTime startOfLastMonth = now.minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfLastMonth = now.withDayOfMonth(1).minusDays(1).withHour(23).withMinute(59).withSecond(59);

        // Lấy các assignment đủ điều kiện
        List<AppointmentAssignment> assignments = appointmentAssignmentRepository
                .findByAppointment_StatusAndStartTimeBetween(
                        AppointmentStatus.PAID, startOfLastMonth, endOfLastMonth);

        if (assignments.isEmpty()) {
            log.info("No assignments to process salary.");
            return;
        }

        Map<ConsultantProfiles, List<AppointmentAssignment>> groupedByConsultant =
                assignments.stream().collect(Collectors.groupingBy(AppointmentAssignment::getConsultant));

        for (Map.Entry<ConsultantProfiles, List<AppointmentAssignment>> entry : groupedByConsultant.entrySet()) {
            ConsultantProfiles consultant = entry.getKey();
            List<AppointmentAssignment> consultantAssignments = entry.getValue();

            double totalAmount = consultantAssignments.stream()
                    .mapToDouble(a -> a.getAppointment().getTotalAmount())
                    .sum();

            double salary = totalAmount * 0.6;

            // Update wallet
            Wallet wallet = walletRepository.findByAccountId(consultant.getAccount().getId());
            if (wallet == null) {
                throw new RuntimeException("Wallet not found for consultant");
            }
            wallet.setBalance(wallet.getBalance() + salary);
            walletRepository.save(wallet);

            // Save Payment
            Payment payment = new Payment();
            payment.setAmount(salary);
            payment.setPaidAt(LocalDateTime.now());
            payment.setMethod("SYSTEM");
            payment.setTransactionCode(UUID.randomUUID().toString());
            payment.setStatus("SUCCESS");
            payment.setType("SALARY");
            payment.setFromAccount("SYSTEM");
            payment.setToAccount(consultant.getAccount().getEmail());
            payment.setDescription("Salary for " + startOfLastMonth.getMonth());

            paymentRepository.save(payment);

            // Mark assignments paid
            consultantAssignments.forEach(a -> a.setPaid(true));
            appointmentAssignmentRepository.saveAll(consultantAssignments);

            log.info("Paid salary {} for consultant {}", salary, consultant.getAccount().getEmail());
        }

        log.info("=== END Monthly Salary Job ===");
    }

}


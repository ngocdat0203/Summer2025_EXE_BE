package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.entity.AppointmentAssignment;
import com.example.lovenhavestopsystem.model.entity.Payment;
import com.example.lovenhavestopsystem.model.entity.Wallet;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import com.example.lovenhavestopsystem.repository.IAppointmentRepository;
import com.example.lovenhavestopsystem.repository.IPaymentRepository;
import com.example.lovenhavestopsystem.repository.IWalletRepository;
import com.example.lovenhavestopsystem.service.inter.IAppointmentService;
import com.example.lovenhavestopsystem.service.inter.IWalletService;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {
    private final IWalletRepository walletRepository;
    private final IAccountRepository accountRepository;
    private final IAppointmentRepository appointmentRepository;
    private final IAppointmentService appointmentService;
    private final PaymentService paymentService;

    @Override
    public void createWallet(int accountId) {
        Wallet wallet = new Wallet();
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        wallet.setAccount(account.get());
        walletRepository.save(wallet);
    }

    public Double depositToWallet(int userId, int amount, String transactionCode ){
        Wallet wallet = walletRepository.findByAccountId(userId);
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
        paymentService.createPayment(
                amount,
                "ATM",
                transactionCode,
                "Success",
                "PAY",
                "VNPay",
                wallet.getAccount().getEmail(),
                "Deposit to wallet #" + wallet.getId()
        );
        return wallet.getBalance();
    }

    @Override
    public Double getBalance(int accountId) {
        Wallet wallet = walletRepository.findByAccountId(accountId);
        return wallet.getBalance();
    }

    @Override
    public void payFromWallet(int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));
        Wallet wallet = walletRepository.findByAccountId(appointment.getCustomer().getId());

        AppointmentAssignment assignment = appointment.getAppointmentAssignment();

        if (assignment == null || assignment.getStartTime() == null || assignment.getEndTime() == null) {
            throw new IllegalStateException("Appointment assignment or its time is not set");
        }

        long minutes = Duration.between(assignment.getStartTime(), assignment.getEndTime()).toMinutes();
        int hours = (int) Math.ceil(minutes / 60.0);
        int pricePerHour = appointment.getService().getPricePerHour().intValue();
        int totalPrice = pricePerHour * hours;

        int deposit = pricePerHour / 2;
        int remainingAmount = totalPrice - deposit;

        if (wallet.getBalance() < remainingAmount) throw new IllegalStateException("Not enough balance");

        wallet.setBalance(wallet.getBalance() - remainingAmount);
        walletRepository.save(wallet);

        String transactionCode = generateTransactionCode();

        paymentService.createPayment(
                remainingAmount,
                "WALLET",
                transactionCode,
                "Success",
                "PAY",
                appointment.getCustomer().getEmail(),
                "LoveHavenStopSystem",
                "Pay the remainder of the appointment #" + appointmentId
        );

        appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.PAID);
    }

    @Override
    public void depositFromWallet(int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));
        Wallet wallet = walletRepository.findByAccountId(appointment.getCustomer().getId());

        double depositAmount = appointment.getService().getPricePerHour() / 2;

        if (wallet.getBalance() < depositAmount) {
            throw new IllegalStateException("Not enough balance in wallet for deposit");
        }

        wallet.setBalance(wallet.getBalance() - depositAmount);
        walletRepository.save(wallet);

        String transactionCode = generateTransactionCode();

        paymentService.createPayment(
                depositAmount,
                "WALLET",
                transactionCode,
                "Success",
                "DEPOSIT",
                appointment.getCustomer().getEmail(),
                "LoveHavenStopSystem",
                "Deposit for appointment #" + appointmentId
        );

        appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.DEPOSITED);
    }


    private String generateTransactionCode() {
        return System.currentTimeMillis() + String.format("%03d", new Random().nextInt(999));
    }
}

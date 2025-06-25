package com.example.lovenhavestopsystem.service.inter;


public interface IWalletService {
    void createWallet(int accountId);
    Double getBalance(int accountId);
    void payFromWallet(int appointmentId);
    void depositFromWallet(int appointmentId);
    Double depositToWallet(int userId, int amount, String transactionCode );
}

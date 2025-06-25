package com.example.lovenhavestopsystem.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.service.inter.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final IWalletService walletService;

    @GetMapping("/wallet/balance")
    public ResponseEntity<BaseResponse<?>> getBalance(@RequestParam int accountId) {
        Double balance = walletService.getBalance(accountId);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, balance));
    }

    @PostMapping("/wallet/pay")
    public ResponseEntity<BaseResponse<?>> payFromWallet(@RequestParam int appointmentId) {
        walletService.payFromWallet(appointmentId);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), "Payment from wallet successful", null));
    }

    @PostMapping("/wallet/deposit-to-wallet")
    public ResponseEntity<BaseResponse<?>> depositToWallet(
            @RequestParam int userId,
            @RequestParam int amount,
            @RequestParam String transactionCode) {

        Double newBalance = walletService.depositToWallet(userId, amount, transactionCode);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), "Wallet top-up successful", newBalance));
    }

}

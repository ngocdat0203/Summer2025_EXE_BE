package com.example.lovenhavestopsystem.repository;

import com.example.lovenhavestopsystem.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByAccountId(int accountId);
}

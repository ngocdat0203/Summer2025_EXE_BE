package com.example.lovenhavestopsystem.user.crud.reposotory;

import com.example.lovenhavestopsystem.user.crud.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, Integer> {
}

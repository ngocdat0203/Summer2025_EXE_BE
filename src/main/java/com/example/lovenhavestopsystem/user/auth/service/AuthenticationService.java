package com.example.lovenhavestopsystem.user.auth.service;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.user.auth.dto.LoginDTO;
import com.example.lovenhavestopsystem.user.auth.jwt.JwtService;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.Role;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        Account account = accountRepository.findByEmailAndDeletedTimeIsNull(loginDTO.getEmail());
        List<RoleName> roles = account.getRoles().stream().map(Role::getName).toList();

        if (authentication.isAuthenticated()) {
            Integer consultantProfileId = null; // Default to null if not a consultant
            if (account.getConsultantProfiles() != null) {
                consultantProfileId = account.getConsultantProfiles().getId();
            }

            return jwtService.generateToken(
                    account.getId(),
                    account.getEmail(),
                    roles,
                    account.getName(),
                    account.getAddress(),
                    consultantProfileId // Pass null if not a consultant
            );
        } else {
            return BaseMessage.VERIFY_FAIL;
        }
    }
}
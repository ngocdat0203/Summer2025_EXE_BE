package com.example.lovenhavestopsystem.user.auth.service;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.exception.BadRequestException;
import com.example.lovenhavestopsystem.user.auth.dto.LoginDTO;
import com.example.lovenhavestopsystem.user.auth.jwt.JwtService;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.Role;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.example.lovenhavestopsystem.user.crud.enums.Status;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
        try {
            System.out.println("✅ Đang xác thực người dùng...");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            if (!authentication.isAuthenticated()) {
                System.out.println("❌ Xác thực thất bại.");
                throw new BadRequestException(BaseMessage.VERIFY_FAIL) ;
            }

            Account account = accountRepository.findByEmailAndDeletedTimeIsNull(loginDTO.getEmail());
            if (account == null) {
                System.out.println("❌ Không tìm thấy tài khoản.");
                throw new BadRequestException(BaseMessage.VERIFY_FAIL) ;
            }

            if (account.getStatus() == null || account.getStatus() != Status.ACTIVE) {
                System.out.println("❌ Tài khoản không hoạt động.");
                throw new BadRequestException(BaseMessage.ACCOUNT_NOT_ACTIVE);
            }

            List<RoleName> roles = account.getRoles().stream()
                    .map(Role::getName)
                    .toList();

            int consultantProfileId = account.getConsultantProfiles() != null
                    ? account.getConsultantProfiles().getId()
                    : 0;

            String token = jwtService.generateToken(
                    account.getId(),
                    account.getEmail(),
                    roles,
                    account.getName(),
                    account.getAddress(),
                    consultantProfileId
            );


            System.out.println("✅ Token sinh ra: " + token);

            return token;

        } catch (BadCredentialsException ex) {
            System.out.println("❌ Sai email hoặc mật khẩu: " + ex.getMessage());
            return BaseMessage.VERIFY_FAIL;
        } catch (Exception ex) {
            System.out.println("❌ Lỗi trong quá trình đăng nhập: " + ex.getMessage());
            ex.printStackTrace(); // log lỗi chi tiết
            return BaseMessage.VERIFY_FAIL;
        }
    }
}

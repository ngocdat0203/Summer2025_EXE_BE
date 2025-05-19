package com.example.lovenhavestopsystem.user.crud.service.inter;

import com.example.lovenhavestopsystem.user.crud.dto.request.AccountCreateDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountForgotPasswordDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountRegisterDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountUpdateDTO;
import com.example.lovenhavestopsystem.user.crud.dto.response.AccountInformationDTO;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAccountService {
    void register(AccountRegisterDTO accountRegisterDTO);

    void updateInfo(int id, AccountUpdateDTO dto);

    AccountInformationDTO getInfo(int id);

    void forgotPassword(AccountForgotPasswordDTO dto);

    Page<Account> getAllAccount(int page, int size);

    void delete(int id);

    void create(AccountCreateDTO dto, List<MultipartFile> images) throws IOException;

    List<AccountCreateDTO> getAllAccountsByRole(List<String> roles);

    void updateStatus(int id, Status status);
}

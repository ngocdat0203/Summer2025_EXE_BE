package com.example.lovenhavestopsystem.user.crud.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.exception.BadRequestException;
import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.service.inter.IConsultantProfileService;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountCreateDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountForgotPasswordDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountRegisterDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountUpdateDTO;
import com.example.lovenhavestopsystem.user.crud.dto.response.AccountInformationDTO;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.Role;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.example.lovenhavestopsystem.user.crud.enums.Status;
import com.example.lovenhavestopsystem.user.crud.mapper.IAccountMapper;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import com.example.lovenhavestopsystem.user.crud.reposotory.IRoleRepository;
import com.example.lovenhavestopsystem.user.crud.service.inter.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepo;

    @Autowired
    private OtpService otpService;

    @Autowired
    @Qualifier("IAccountMapperImpl")
    private IAccountMapper accountMapper;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IConsultantProfileService consultantProfileService;



    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void register(AccountRegisterDTO accountRegisterDTO) {
        boolean isVerified = otpService.isOtpVerified(accountRegisterDTO.getEmail());
        if (!isVerified) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }

        Account exist = accountRepo.findByEmailAndDeletedTimeIsNull(accountRegisterDTO.getEmail());
        if (exist != null) {
            throw new BadRequestException(BaseMessage.ALREADY_EXISTS);
        }

        Account account = accountMapper.registerDtoToAccount(accountRegisterDTO);
        account.setPassword(encoder.encode(accountRegisterDTO.getPassword()));
        account.setEmail(accountRegisterDTO.getEmail());
//        /*account.setUsername(accountRegisterDTO.getEmail());
//        account.setStatus(Status.ACTIVE);*/

        Role userRole = roleRepository.findByName(RoleName.USER);

        if (userRole == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }

        account.setRoles(List.of(userRole));


        accountRepo.save(account);
    }

    @Override
    public void updateInfo(int id, AccountUpdateDTO dto) {
        Account account = accountRepo.findByIdAndDeletedTimeIsNull(id);
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            account.setPassword(encoder.encode(dto.getPassword()));
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            account.setFullName(dto.getName());
        }
        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            account.setPhone(dto.getPhone());
        }
        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            account.setAddress(dto.getAddress());
        }


        accountRepo.save(account);
    }

    @Override
    public AccountInformationDTO getInfo(int id) {
        Account account = accountRepo.findByIdAndDeletedTimeIsNull(id);
        if (account == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }
        return accountMapper.accountToAccountInfoResponseDTO(account);
    }

    @Override
    public void forgotPassword(AccountForgotPasswordDTO dto) {
        boolean isVerified = otpService.isOtpVerified(dto.getEmail());
        if (!isVerified) {
            throw new NotFoundException(BaseMessage.VERIFY_FAIL);
        }

        Account account = accountRepo.findByEmailAndDeletedTimeIsNull(dto.getEmail());
        if (account == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }
        account.setPassword(encoder.encode(dto.getPassword()));
        accountRepo.save(account);
    }

    @Override
    public Page<Account> getAllAccount(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return accountRepo.getAllByDeletedTimeIsNull(pageable);
    }

    @Override
    public void delete(int id) {
        Account account = accountRepo.findByIdAndDeletedTimeIsNull(id);
        if (account == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }
        account.setDeletedTime(LocalDateTime.now());
        accountRepo.save(account);
    }

    @Override
    public void create(AccountCreateDTO dto, List<MultipartFile> images) throws IOException {
        Account exist = accountRepo.findByEmailAndDeletedTimeIsNull(dto.getEmail());
        if (exist != null) {
            throw new BadRequestException(BaseMessage.ALREADY_EXISTS);
        }

        Account newAccount = accountMapper.createDtoToAccount(dto);
        newAccount.setEmail(dto.getEmail());
        newAccount.setFullName(dto.getName());
        newAccount.setPhone(dto.getPhone());
        newAccount.setAddress(dto.getAddress());
        newAccount.setStatus(Status.ACTIVE);
        newAccount.setPassword(encoder.encode(dto.getPassword()));

        List<Role> roles = roleRepository.findByNameIn(dto.getRoles());
        if (roles.isEmpty()) {
            throw new BadRequestException(BaseMessage.INVALID_DATA_INPUT);
        }

        newAccount.setRoles(new ArrayList<>(roles));

        accountRepo.save(newAccount);

        boolean isConsultant = roles.stream().anyMatch(role -> role.getName().equals(RoleName.CONSULTANT));
        if (isConsultant) {
            consultantProfileService.create(newAccount.getId(), dto.getExperience(), images);
        }
    }

    @Override
    public List<AccountCreateDTO> getAllAccountsByRole(List<String> roles) {
        try {

            List<RoleName> roleNames = roles.stream()
                    .map(RoleName::valueOf)
                    .toList();
            List<Role> rolesList = roleRepository.getRolesByNameIn(roleNames);
            List<Account> accounts = accountRepo.getAccountsByListRole(rolesList);
            List<AccountCreateDTO> accountCreateDTOS = new ArrayList<>();
            for (Account account : accounts) {
                AccountCreateDTO accountCreateDTO = new AccountCreateDTO();
                accountCreateDTO.setEmail(account.getEmail());
                accountCreateDTO.setName(account.getFullName());
                accountCreateDTO.setPhone(account.getPhone());
                accountCreateDTO.setStatus(account.getStatus().toString());
                accountCreateDTOS.add(accountCreateDTO);
            }
            return accountCreateDTOS;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void updateStatus(int id, Status status) {
        Account account = accountRepo.findByIdAndDeletedTimeIsNull(id);
        if (account == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }
        account.setStatus(status);
        accountRepo.save(account);
    }

}

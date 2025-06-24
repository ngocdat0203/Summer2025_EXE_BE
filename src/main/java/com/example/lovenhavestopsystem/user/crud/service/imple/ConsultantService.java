package com.example.lovenhavestopsystem.user.crud.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.exception.BadRequestException;
import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.repository.IConsultantProfileRepository;
import com.example.lovenhavestopsystem.service.imple.ImageService;
import com.example.lovenhavestopsystem.user.crud.dto.request.AccountRegisterDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.ConsultantProfilesDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.ConsultantRegisterDTO;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.example.lovenhavestopsystem.user.crud.entity.Role;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.example.lovenhavestopsystem.user.crud.enums.Status;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import com.example.lovenhavestopsystem.user.crud.reposotory.IRoleRepository;
import com.example.lovenhavestopsystem.user.crud.service.inter.IAccountService;
import com.example.lovenhavestopsystem.user.crud.service.inter.IConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultantService implements IConsultantService {

    @Autowired
    private IAccountRepository accountRepo;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRoleRepository roleRepo;

    @Autowired
    private IConsultantProfileRepository consultantProfileRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private OtpService otpService;

    public void register(ConsultantRegisterDTO consultantProfilesRegisterDTO) {
        accountService.register(consultantProfilesRegisterDTO.getAccountRegisterDTO());

        Account account = accountRepo.findByEmailAndDeletedTimeIsNull(consultantProfilesRegisterDTO.getAccountRegisterDTO().getEmail());

        if (account == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }

        if (account.getRoles() != null && account.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.CONSULTANT))) {
            throw new BadRequestException(BaseMessage.ALREADY_REGISTERED);
        }

        if (account.getRoles() != null && account.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.USER))) {
            Role consultantRole = roleRepo.findByName(RoleName.CONSULTANT);

            if (consultantRole == null) {
                throw new NotFoundException(BaseMessage.NOT_FOUND);
            }

            // Create a mutable copy of the roles list
            List<Role> roles = new ArrayList<>(account.getRoles());
            roles.add(consultantRole);
            account.setRoles(roles);
            account.setStatus(Status.INACTIVE);
            accountRepo.save(account);
        }

        ConsultantProfiles consultantProfiles = new ConsultantProfiles();
        consultantProfiles.setAccount(account);
        consultantProfiles.setBio(consultantProfilesRegisterDTO.getBio());
        consultantProfiles.setExpertise(consultantProfilesRegisterDTO.getExpertise());

        consultantProfileRepository.save(consultantProfiles);
    }

    @Override
    public void create(int accountId, String bio, String expertise, List<MultipartFile> images) throws IOException {
        Account account = accountRepo.findById(accountId).orElseThrow(
                () -> new NotFoundException(BaseMessage.NOT_FOUND));

        ConsultantProfiles consultantProfiles = new ConsultantProfiles();
        consultantProfiles.setAccount(account);
        consultantProfiles.setBio(bio);
        consultantProfiles.setExpertise(expertise);

        /*if (images != null && !images.isEmpty()) {
            imageService.uploadFiles(images, null, therapist.getId());
        }*/
        consultantProfileRepository.save(consultantProfiles);
    }

    @Override
    public Page<ConsultantProfiles> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return consultantProfileRepository.getAllByDeletedTimeIsNull(pageable);
    }

    @Override
    public List<ConsultantProfiles> getAllByServiceId(int serviceId) {
        return consultantProfileRepository.getAllByServiceId(serviceId);
    }

    @Override
    public void updateConsultantInfo(ConsultantProfilesDTO dto, List<MultipartFile> images) throws IOException {
        ConsultantProfiles consultantProfiles = consultantProfileRepository.findByAccountId(dto.getAccountId());
        if (consultantProfiles == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            consultantProfiles.getAccount().setPassword(dto.getPassword());
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            consultantProfiles.getAccount().setName(dto.getName());
        }
        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            consultantProfiles.getAccount().setPhone(dto.getPhone());
        }
        if (dto.getExpertise() != null) {
            consultantProfiles.setExpertise(dto.getExpertise());
        }
        if (dto.getBio() != null && !dto.getBio().isEmpty()) {
            consultantProfiles.setBio(dto.getBio());
        }
        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            consultantProfiles.getAccount().setAddress(dto.getAddress());
        }


        consultantProfileRepository.save(consultantProfiles);

        /*if (images != null && !images.isEmpty()) {
            imageService.uploadFiles(images, null, therapist.getId(), null);
        }*/
    }

    @Override
    public ConsultantProfiles getConsultantByAccountId(int accountId) {
        return consultantProfileRepository.findByAccountId(accountId);
    }

    @Override
    public void updateConsultant(ConsultantProfilesDTO dto) throws IOException {
        ConsultantProfiles consultantProfiles = consultantProfileRepository.findByAccountId(dto.getAccountId());
        if (consultantProfiles == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            consultantProfiles.getAccount().setPassword(dto.getPassword());
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            consultantProfiles.getAccount().setName(dto.getName());
        }
        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            consultantProfiles.getAccount().setPhone(dto.getPhone());
        }
        if (dto.getExpertise() != null && !dto.getExpertise().isEmpty()) {
            consultantProfiles.setExpertise(dto.getExpertise());
        }
        if (dto.getBio() != null && !dto.getBio().isEmpty()) {
            consultantProfiles.setBio(dto.getBio());
        }
        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            consultantProfiles.getAccount().setAddress(dto.getAddress());
        }
        consultantProfileRepository.save(consultantProfiles);
    }

    @Override
    public void updateConsultantStatus(int accountId, String status) throws IOException {
        ConsultantProfiles consultantProfiles = consultantProfileRepository.findByAccountId(accountId);

        if (consultantProfiles == null) {
            throw new NotFoundException(BaseMessage.NOT_FOUND);
        }

        consultantProfiles.getAccount().setStatus(Status.ACTIVE);

        accountRepo.save(consultantProfiles.getAccount());
        consultantProfileRepository.save(consultantProfiles);

    }
}

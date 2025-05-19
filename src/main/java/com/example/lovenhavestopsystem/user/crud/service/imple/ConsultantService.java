package com.example.lovenhavestopsystem.user.crud.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.repository.IConsultantProfileRepository;
import com.example.lovenhavestopsystem.service.imple.ImageService;
import com.example.lovenhavestopsystem.user.crud.dto.request.ConsultantProfilesDTO;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import com.example.lovenhavestopsystem.user.crud.service.inter.IConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ConsultantService implements IConsultantService {

    @Autowired
    private IAccountRepository accountRepo;

    @Autowired
    private IConsultantProfileRepository consultantProfileRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public void create(int accountId, String bio, List<MultipartFile> images) throws IOException {
        Account account = accountRepo.findById(accountId).orElseThrow(
                () -> new NotFoundException(BaseMessage.NOT_FOUND));

        ConsultantProfiles therapist = new ConsultantProfiles();
        therapist.setAccount(account);
        therapist.setBio(bio);
        if (images != null && !images.isEmpty()) {
            imageService.uploadFiles(images, null, therapist.getId());
        }
        consultantProfileRepository.save(therapist);
    }

    @Override
    public Page<ConsultantProfiles> getAll(int page, int size) {
        return null;
    }

    @Override
    public List<ConsultantProfiles> getAllByServiceId(int serviceId) {
        return List.of();
    }

    @Override
    public void updateTherapistInfo(ConsultantProfilesDTO dto, List<MultipartFile> images) throws IOException {

    }

    @Override
    public ConsultantProfiles getTherapistByAccountId(int accountId) {
        return null;
    }

    @Override
    public List<ConsultantProfiles> getTherapistByServiceDetailId(int serviceDetailId) {
        return List.of();
    }

    @Override
    public void updateTherapist(ConsultantProfilesDTO therapistInfoDTO) throws IOException {

    }
}

package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.service.inter.IConsultantProfileService;
import com.example.lovenhavestopsystem.service.inter.IServiceConsultantProfileService;
import com.example.lovenhavestopsystem.user.crud.dto.request.ConsultantProfilesDTO;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ConsultantProfileService implements IConsultantProfileService {

    @Override
    public void create(int accountId, int experience, List<MultipartFile> images) throws IOException {

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

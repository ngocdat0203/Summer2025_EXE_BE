package com.example.lovenhavestopsystem.user.crud.service.inter;

import com.example.lovenhavestopsystem.user.crud.dto.request.ConsultantProfilesDTO;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IConsultantService {
    void create(int accountId, String bio, List<MultipartFile> images) throws IOException;

    Page<ConsultantProfiles> getAll(int page, int size);

    List<ConsultantProfiles> getAllByServiceId(int serviceId);

    void updateTherapistInfo(ConsultantProfilesDTO dto, List<MultipartFile> images) throws IOException;

    ConsultantProfiles getTherapistByAccountId(int accountId);

    List<ConsultantProfiles> getTherapistByServiceDetailId(int serviceDetailId);

    void updateTherapist(ConsultantProfilesDTO therapistInfoDTO) throws IOException;
}

package com.example.lovenhavestopsystem.user.crud.service.inter;

import com.example.lovenhavestopsystem.user.crud.dto.request.ConsultantProfilesDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.ConsultantRegisterDTO;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.example.lovenhavestopsystem.user.crud.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IConsultantService {

    void register(ConsultantRegisterDTO consultantProfilesRegisterDTO);

    void create(int accountId, String bio, String expertise, List<MultipartFile> images) throws IOException;

    Page<ConsultantProfiles> getAll(int page, int size);

    List<ConsultantProfiles> getAllByServiceId(int serviceId);

    void updateConsultantInfo(ConsultantProfilesDTO dto, List<MultipartFile> images) throws IOException;

    ConsultantProfiles getConsultantByAccountId(int accountId);


    void updateConsultant(ConsultantProfilesDTO therapistInfoDTO) throws IOException;

    void updateConsultantStatus(int consultantId, Status status) throws IOException;
}

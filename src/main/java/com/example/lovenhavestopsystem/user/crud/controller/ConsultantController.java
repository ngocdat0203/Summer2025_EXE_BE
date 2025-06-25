package com.example.lovenhavestopsystem.user.crud.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.user.crud.dto.request.ConsultantProfilesDTO;
import com.example.lovenhavestopsystem.user.crud.dto.request.ConsultantRegisterDTO;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.example.lovenhavestopsystem.user.crud.enums.Status;
import com.example.lovenhavestopsystem.user.crud.service.inter.IConsultantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ConsultantController {

    @Autowired
    private IConsultantService consultantService;

    @PostMapping("register-consultant")
    public ResponseEntity<BaseResponse<Void>> registerConsultant(@RequestBody ConsultantRegisterDTO consultantProfilesRegisterDTO) {
        consultantService.register(consultantProfilesRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse<>(HttpStatus.CREATED.value(), BaseMessage.REGISTER_SUCCESS));
    }

    @GetMapping("get-all-consultants")
    public ResponseEntity<BaseResponse<Page<ConsultantProfiles>>> getAllTherapist(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS,
                        consultantService.getAll(page, size)));
    }

    @PutMapping("update-consultant-info")
    public ResponseEntity<BaseResponse<Void>> updateTherapistInfo(
            @RequestPart("request") String request,
            @RequestPart(required = false) List<MultipartFile> images) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ConsultantProfilesDTO dto = objectMapper.readValue(request, ConsultantProfilesDTO.class);
        consultantService.updateConsultantInfo(dto, images);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.UPDATE_SUCCESS));
    }

    @PutMapping("update-consultant")
    public ResponseEntity<BaseResponse<Void>> updateTherapist(@RequestBody ConsultantProfilesDTO therapistInfoDTO ) throws IOException {
        consultantService.updateConsultant(therapistInfoDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.UPDATE_SUCCESS));
    }

    @GetMapping("get-consultant-by-account-id/{accountId}")
    public ResponseEntity<BaseResponse<ConsultantProfiles>> getTherapistByAccountId(@PathVariable int accountId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS,
                        consultantService.getConsultantByAccountId(accountId)));
    }

    @PostMapping("update-consultant-status")
    public ResponseEntity<BaseResponse<Void>> updateConsultantStatus(@RequestParam int id, @RequestParam Status status) throws IOException {
        consultantService.updateConsultantStatus(id, status);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.UPDATE_SUCCESS));
    }
}

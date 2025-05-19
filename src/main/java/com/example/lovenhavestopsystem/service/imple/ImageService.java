package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.dto.response.ImageResponseDTO;
import com.example.lovenhavestopsystem.dto.response.ImageUploadResponseDTO;
import com.example.lovenhavestopsystem.model.entity.Image;
import com.example.lovenhavestopsystem.model.entity.Service;
import com.example.lovenhavestopsystem.repository.IConsultantProfileRepository;
import com.example.lovenhavestopsystem.repository.IImageRepository;
import com.example.lovenhavestopsystem.repository.IServiceRepository;
import com.example.lovenhavestopsystem.service.inter.IImageService;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ImageService implements IImageService {

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private IServiceRepository serviceRepository;

    @Autowired
    private IConsultantProfileRepository consultantProfileRepository;

    @Autowired
    private IImageRepository imageRepository;

    @Override
    public void uploadFiles(List<MultipartFile> files, Integer serviceId, Integer consultantId) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new BadRequestException(BaseMessage.INVALID_DATA_INPUT);
        }

        List<com.example.lovenhavestopsystem.model.entity.Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = firebaseStorageService.uploadFile(file);
            com.example.lovenhavestopsystem.model.entity.Image image = new com.example.lovenhavestopsystem.model.entity.Image();
            image.setUrl(url);

            if (serviceId != null) {
                Service service = serviceRepository.findById(serviceId)
                        .orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND));
                image.setService(service);
            } else if (consultantId != null) {
                ConsultantProfiles consultantProfiles = consultantProfileRepository.findById(consultantId)
                        .orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND));
                image.setConsultantProfiles(consultantProfiles);
            } else {
                throw new BadRequestException(BaseMessage.INVALID_DATA_INPUT);
            }

            images.add(image);
        }

        imageRepository.saveAll(images);
    }

    @Override
    public ImageUploadResponseDTO uploadFilesWithResponse(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new com.example.lovenhavestopsystem.core.exception.BadRequestException(BaseMessage.INVALID_DATA_INPUT);
        }

        List<ImageResponseDTO> successList = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String url = firebaseStorageService.uploadFile(file);

                com.example.lovenhavestopsystem.model.entity.Image image = new Image();
                image.setUrl(url);

                image = imageRepository.save(image);

                successList.add(new ImageResponseDTO(image.getId(), image.getUrl()));

            } catch (Exception e) {
                failedFiles.add(file.getOriginalFilename());
            }
        }

        return new ImageUploadResponseDTO(successList, failedFiles);
    }
}

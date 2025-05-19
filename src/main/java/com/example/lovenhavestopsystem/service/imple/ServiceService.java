package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.exception.BadRequestException;
import com.example.lovenhavestopsystem.dto.request.ServiceRequestDTO;
import com.example.lovenhavestopsystem.model.entity.Service;
import com.example.lovenhavestopsystem.repository.IConsultantProfileRepository;
import com.example.lovenhavestopsystem.repository.IServiceRepository;
import com.example.lovenhavestopsystem.service.inter.IConsultantProfileService;
import com.example.lovenhavestopsystem.service.inter.IServiceConsultantProfileService;
import com.example.lovenhavestopsystem.service.inter.IServiceService;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.lovenhavestopsystem.model.entity.*; // Assuming this is the correct class
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {

    @Autowired
    private IServiceRepository serviceRepository;

    @Autowired
    private IServiceConsultantProfileService serviceConsultantProfileService;

    @Override
    public boolean create(ServiceRequestDTO serviceRequestDTO) {
        Service service = new Service();

        List<Integer> consultantProfileServices = serviceRequestDTO.getConsultantProfileIds();

        if(serviceRepository.existsByName(serviceRequestDTO.getName())) {
            throw new BadRequestException("Service name already exists");
        }

        service.setName(serviceRequestDTO.getName());
        service.setDescription(serviceRequestDTO.getDescription());
        service.setPricePerHour(serviceRequestDTO.getPricePerHour());


        serviceRepository.save(service);

        if(consultantProfileServices == null ){
            return true;
        }

        for(Integer consultantProfileId : consultantProfileServices) {
            serviceConsultantProfileService.create(consultantProfileId, service.getId());
        }
        return true;

    }

    @Override
    public boolean delete(int id) {
        Service service = serviceRepository.findById(id).orElse(null);
        if(service == null) {
            throw new BadRequestException("Service not found");
        }
        if(service.getDeletedTime() != null) {
            throw new BadRequestException("Service already deleted");
        }
        service.setDeletedTime(LocalDateTime.now());
        serviceRepository.save(service);
        return true;
    }

    @Override
    public boolean update(ServiceRequestDTO serviceDTO, int id) {
        Service service = serviceRepository.findById(id).orElse(null);
        if(service == null) {
            throw new BadRequestException("Service not found");
        }
        if(service.getDeletedTime() != null) {
            throw new BadRequestException("Service already deleted");
        }
        if(serviceRepository.existsByNameAndIdNot(serviceDTO.getName(), id)) {
            throw new BadRequestException("Service name already exists");
        }
        service.setName(serviceDTO.getName());
        service.setDescription(serviceDTO.getDescription());
        service.setPricePerHour(serviceDTO.getPricePerHour());
        service.setLastUpdatedTime(LocalDateTime.now());
        serviceRepository.save(service);

        return true;
    }

    @Override
    public Page<Service> getAllIsNotDeletedServicePaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return serviceRepository.findAllByDeletedTimeIsNull(pageable);
    }


}

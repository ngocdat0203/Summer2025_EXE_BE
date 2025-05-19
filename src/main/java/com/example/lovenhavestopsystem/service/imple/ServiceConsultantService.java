package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.model.entity.ServiceConsultant;
import com.example.lovenhavestopsystem.repository.IConsultantProfileRepository;
import com.example.lovenhavestopsystem.repository.IServiceConsultantProfileRepository;
import com.example.lovenhavestopsystem.repository.IServiceRepository;
import com.example.lovenhavestopsystem.service.inter.IConsultantProfileService;
import com.example.lovenhavestopsystem.service.inter.IServiceConsultantProfileService;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceConsultantService implements IServiceConsultantProfileService {

    @Autowired
    private IServiceConsultantProfileRepository serviceConsultantProfileRepository;

    @Autowired
    private IConsultantProfileRepository consultantProfileRepository;

    @Autowired
    private IServiceRepository serviceRepository;

    @Override
    public boolean create(int serviceId, int consultantId) {

        ServiceConsultant serviceConsultant = new ServiceConsultant();

        com.example.lovenhavestopsystem.model.entity.Service service = serviceRepository.getServicesByIdAndDeletedTimeIsNull(serviceId);

        if(service == null){
            return false;
        }

        serviceConsultant.setService(service);

        ConsultantProfiles consultantProfiles = consultantProfileRepository.getByIdAndDeletedTimeIsNull(consultantId);

        if(consultantProfiles == null){
            return false;
        }

        serviceConsultant.setConsultantProfiles(consultantProfiles);

        serviceConsultantProfileRepository.save(serviceConsultant);
        return true;

    }
}

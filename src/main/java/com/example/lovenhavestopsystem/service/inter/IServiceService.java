package com.example.lovenhavestopsystem.service.inter;

import com.example.lovenhavestopsystem.dto.request.ServiceRequestDTO;
import com.example.lovenhavestopsystem.dto.response.PopularServiceDTO;
import com.example.lovenhavestopsystem.model.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServiceService {
    boolean create(ServiceRequestDTO serviceRequestDTO);
    boolean delete(int id);
    boolean update(ServiceRequestDTO serviceDTO, int id);
    Page<Service> getAllIsNotDeletedServicePaging(int page, int size);
    PopularServiceDTO getMostPopularService();
}

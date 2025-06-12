package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.exception.BadRequestException;
import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.dto.request.AppointmentCreateDTO;
import com.example.lovenhavestopsystem.mapper.AppointmentMapper;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import com.example.lovenhavestopsystem.repository.IAppointmentRepository;
import com.example.lovenhavestopsystem.repository.IServiceRepository;
import com.example.lovenhavestopsystem.service.inter.IAppointmentService;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {
    private final IAppointmentRepository appointmentRepository;
    private final IAccountRepository accountRepository;
    private final IServiceRepository serviceRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(IAppointmentRepository appointmentRepository, IAccountRepository accountRepository, IServiceRepository serviceRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.accountRepository = accountRepository;
        this.serviceRepository = serviceRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public int createAppointment(AppointmentCreateDTO dto) {
        Optional<Account> accountOptional = accountRepository.findById(dto.getAccountId());
        if(accountOptional.isEmpty()) {
            throw new BadRequestException("Account not found");
        }
        Optional<com.example.lovenhavestopsystem.model.entity.Service> serviceOptional = serviceRepository.findById(dto.getServiceId());
        if(serviceOptional.isEmpty()) {
            throw new BadRequestException("Service not found");
        }
        Appointment appointment = appointmentMapper.toAppointment(dto);
        appointment.setCustomer(accountOptional.get());
        appointment.setService(serviceOptional.get());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointmentRepository.save(appointment);
        return appointment.getId();
    }

    @Override
    public List<Appointment> getByCustomerId(int id) {
        return appointmentRepository.getAllByCustomer_Id(id);
    }

    @Override
    public List<Appointment> getByCity(String city) {
        return appointmentRepository.getAllByCity(city);
    }

    @Override
    public void updateAppointmentStatus(int id, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Appointment not found"));
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAllAppointmentsByConsultantId(int id) {
        return appointmentRepository.findByAppointmentAssignment_Consultant_Id(id);
    }
}

package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.dto.response.MostConsultantDTO;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.entity.AppointmentAssignment;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import com.example.lovenhavestopsystem.repository.IAppointmentAssignmentRepo;
import com.example.lovenhavestopsystem.repository.IAppointmentRepository;
import com.example.lovenhavestopsystem.repository.IConsultantProfileRepository;
import com.example.lovenhavestopsystem.service.inter.IAppointmentAssignmentService;
import com.example.lovenhavestopsystem.service.inter.IAppointmentService;
import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentAssignmentService implements IAppointmentAssignmentService {
    private final IAppointmentAssignmentRepo repo;
    private final IAppointmentRepository appointmentRepo;
    private final IConsultantProfileRepository consultantProfileRepo;
    private final IAppointmentService appointmentService;

    public AppointmentAssignmentService(IAppointmentAssignmentRepo repo, IAppointmentRepository appointmentRepo, IConsultantProfileRepository consultantProfileRepo, IAppointmentService appointmentService) {
        this.repo = repo;
        this.appointmentRepo = appointmentRepo;
        this.consultantProfileRepo = consultantProfileRepo;
        this.appointmentService = appointmentService;
    }


    @Override
    public void createAppointmentAssignment(int appointmentId, int consultantId) {
        Optional<Appointment> appointment = appointmentRepo.findById(appointmentId);
        if (appointment.isEmpty()) {
            throw new NotFoundException("Appointment not found");
        }

        Optional<ConsultantProfiles> consultantProfile = consultantProfileRepo.findById(consultantId);
        if (consultantProfile.isEmpty()) {
            throw new NotFoundException("Consultant profile not found");
        }
        AppointmentAssignment assignment = new AppointmentAssignment();
        assignment.setAppointment(appointment.get());
        assignment.setConsultant(consultantProfile.get());
        assignment.setAcceptedAt(LocalDateTime.now());
        assignment.setPaid(false);
        repo.save(assignment);
        appointment.get().setStatus(AppointmentStatus.TAKEN);
        appointmentRepo.save(appointment.get());
    }

    @Override
    public void updateAppointmentAssignmentTime(int id, LocalDateTime startTime, LocalDateTime endTime) {
        AppointmentAssignment assignment = repo.findById(id).orElseThrow(() -> new NotFoundException("Appointment assignment not found"));
        assignment.setStartTime(startTime);
        assignment.setEndTime(endTime);
        repo.save(assignment);
        int appointmentId = assignment.getAppointment().getId();
        appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.COMPLETED);
    }

    @Override
    public AppointmentAssignment getAssignmentByAppointmentId(int id) {
        return repo.findByAppointment_Id(id);
    }

    @Override
    public MostConsultantDTO getMostConsulted() {
        List<AppointmentAssignment> assignments = repo.findAll();
        if (assignments.isEmpty()) {
            throw new NotFoundException("No appointment assignments found");
        }
        ConsultantProfiles mostConsulted = null;
        int maxCount = 0;
        for (AppointmentAssignment assignment : assignments) {
            ConsultantProfiles consultant = assignment.getConsultant();
            int count = (int) assignments.stream().filter(a -> a.getConsultant().equals(consultant)).count();
            if (count > maxCount) {
                maxCount = count;
                mostConsulted = consultant;
            }
        }
        if (mostConsulted == null) {
            throw new NotFoundException("No consultant found");
        }
        MostConsultantDTO mostConsultedDTO = new MostConsultantDTO();
        mostConsultedDTO.setConsultantId(mostConsulted.getId());
        mostConsultedDTO.setConsultantName(mostConsulted.getAccount().getName());
        mostConsultedDTO.setUsageCount(maxCount);
        return mostConsultedDTO;
    }
}

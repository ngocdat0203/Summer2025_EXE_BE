package com.example.lovenhavestopsystem.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.dto.request.AppointmentCreateDTO;
import com.example.lovenhavestopsystem.dto.response.MostConsultantDTO;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.entity.AppointmentAssignment;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import com.example.lovenhavestopsystem.service.inter.IAppointmentAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AppointmentAssignmentController {
    private final IAppointmentAssignmentService service;

    public AppointmentAssignmentController(IAppointmentAssignmentService appointmentAssignmentService) {
        this.service = appointmentAssignmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Void>> createAppointmentAssignment(@RequestParam int appointmentId, @RequestParam int consultantId) {
        service.createAppointmentAssignment(appointmentId, consultantId);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.CREATED.value(), BaseMessage.CREATE_SUCCESS));
    }

    @PutMapping("/update-time")
    public ResponseEntity<BaseResponse<Void>> updateAppointmentStatus(@RequestParam int id,
                                                                      @RequestParam LocalDateTime startTime,
                                                                      @RequestParam LocalDateTime endTime) {
        service.updateAppointmentAssignmentTime(id, startTime, endTime);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.UPDATE_SUCCESS));
    }

    @GetMapping("/get-by-appointment")
    public ResponseEntity<BaseResponse<AppointmentAssignment>> getByAppointmentId(@RequestParam int appointmentId) {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(),
                BaseMessage.GET_SUCCESS,
                service.getAssignmentByAppointmentId(appointmentId)));
    }
    @GetMapping("/get-most-consulted")
    public ResponseEntity<BaseResponse<MostConsultantDTO>> getMostConsulted() {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(),
                BaseMessage.GET_SUCCESS,
                service.getMostConsulted()));
    }
}

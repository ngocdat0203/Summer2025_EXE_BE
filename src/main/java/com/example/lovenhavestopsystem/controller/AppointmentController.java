package com.example.lovenhavestopsystem.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.dto.request.AppointmentCreateDTO;
import com.example.lovenhavestopsystem.model.entity.Appointment;
import com.example.lovenhavestopsystem.model.enums.AppointmentStatus;
import com.example.lovenhavestopsystem.service.inter.IAppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Integer>> createAppointment(@RequestBody AppointmentCreateDTO appointment) {
        appointmentService.createAppointment(appointment);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.CREATED.value(), BaseMessage.CREATE_SUCCESS));
    }

    @GetMapping("/get-by-customer-id")
    public ResponseEntity<BaseResponse<List<Appointment>>> getAllByCustomerId(@RequestParam int customerId) {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(),
                BaseMessage.GET_SUCCESS,
                appointmentService.getByCustomerId(customerId)));
    }

    @GetMapping("/get-by-city")
    public ResponseEntity<BaseResponse<List<Appointment>>> getAllByCity(@RequestParam String city) {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(),
                BaseMessage.GET_SUCCESS,
                appointmentService.getByCity(city)));
    }

    @PutMapping("/update-status")
    public ResponseEntity<BaseResponse<Void>> updateAppointmentStatus(@RequestParam int id,
                                                                      @RequestParam AppointmentStatus status) {
        appointmentService.updateAppointmentStatus(id, status);
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.UPDATE_SUCCESS));
    }

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<Appointment>>> getAll() {
        return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, appointmentService.getAllAppointments()));
    }
}

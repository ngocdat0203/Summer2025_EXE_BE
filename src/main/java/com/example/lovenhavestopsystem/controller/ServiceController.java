package com.example.lovenhavestopsystem.controller;


import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.dto.request.ServiceRequestDTO;
import com.example.lovenhavestopsystem.service.imple.ServiceService;
import com.example.lovenhavestopsystem.service.inter.IServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private IServiceService iService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody ServiceRequestDTO serviceRequestDTO) {
        iService.create(serviceRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.CREATED.value(), BaseMessage.CREATE_SUCCESS));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse<Void>> update(@RequestBody ServiceRequestDTO serviceRequestDTO, @PathVariable int id) {
        iService.update(serviceRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.UPDATE_SUCCESS));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        iService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.DELETE_SUCCESS));
    }



}

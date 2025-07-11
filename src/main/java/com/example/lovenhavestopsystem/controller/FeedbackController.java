package com.example.lovenhavestopsystem.controller;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.base.BaseResponse;
import com.example.lovenhavestopsystem.dto.request.FeedbackRequestDTO;
import com.example.lovenhavestopsystem.model.entity.Feedback;
import com.example.lovenhavestopsystem.service.inter.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private final IFeedbackService iFeedbackService;

    @Autowired
    public FeedbackController(IFeedbackService iFeedbackService) {
        this.iFeedbackService = iFeedbackService;
    }

    @PostMapping("create")
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody FeedbackRequestDTO feedbackRequestDTO)  {
        iFeedbackService.create(feedbackRequestDTO.getContent(),feedbackRequestDTO.getConsultantId(), feedbackRequestDTO.getCustomerId());
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.CREATE_SUCCESS));
    }

    @PostMapping("/reply/{feedbackId}")
    public ResponseEntity<BaseResponse<Void>> reply(@PathVariable int feedbackId, @RequestBody String reply) {
        iFeedbackService.reply(reply, feedbackId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.CREATE_SUCCESS));
    }

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<List<Feedback>>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, iFeedbackService.findAll(page, size)));
    }

    @GetMapping("/getByCus")
    public ResponseEntity<BaseResponse<List<Feedback>>> getByCusId(@RequestParam int customerId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, iFeedbackService.findAllByCustomer(customerId, page, size)));
    }

    @GetMapping("/getByThe")
    public ResponseEntity<BaseResponse<List<Feedback>>> getByTheId(@RequestParam int therapistId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK.value(), BaseMessage.GET_SUCCESS, iFeedbackService.findAllByConsultant(therapistId, page, size)));
    }

}

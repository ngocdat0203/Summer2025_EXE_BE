package com.example.lovenhavestopsystem.service.inter;


import com.example.lovenhavestopsystem.model.entity.Feedback;

import java.util.List;

public interface IFeedbackService {
    void create(String content, int therapistId, int customerId);
    void reply(String reply, int feedbackId);
    List<Feedback> findAll(int page, int size);
    List<Feedback> findAllByCustomer(int customerId, int page, int size);
    List<Feedback> findAllByConsultant(int consultantId, int page, int size);
}

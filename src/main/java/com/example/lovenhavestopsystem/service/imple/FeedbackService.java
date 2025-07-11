package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.model.entity.Feedback;
import com.example.lovenhavestopsystem.repository.IFeedbackRepository;
import com.example.lovenhavestopsystem.service.inter.IFeedbackService;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import com.example.lovenhavestopsystem.user.crud.reposotory.IConsultantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FeedbackService implements IFeedbackService {

    private IFeedbackRepository feedbackRepository;
    private IAccountRepository accountRepository;
    private IConsultantRepository therapistRepository;


    public FeedbackService(IFeedbackRepository feedbackRepository, IAccountRepository accountRepository, IConsultantRepository therapistRepository) {
        this.feedbackRepository = feedbackRepository;
        this.accountRepository = accountRepository;
        this.therapistRepository = therapistRepository;
    }
    @Override
    public void create(String content, int therapistId, int customerId) {

        Feedback feedback = new Feedback();
        feedback.setBody(content);

        boolean isCustomerExist = accountRepository.existsById(customerId);

        boolean isTherapistExist = therapistRepository.existsById(therapistId);

        if (!isCustomerExist || !isTherapistExist) {
            throw new RuntimeException(BaseMessage.NOT_FOUND);
        }

        feedback.setCustomerId(customerId);
        feedback.setConsultantId(therapistId);



        feedbackRepository.save(feedback);
    }

    @Override
    public void reply(String reply, int feedbackId) {
        Optional<Feedback> feedbackOptions = feedbackRepository.findById(feedbackId);

        if (feedbackOptions.isEmpty()) {
            throw new RuntimeException(BaseMessage.NOT_FOUND);
        }

        Feedback feedback = feedbackOptions.get();
        feedback.setReply(reply);

        feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbackPage = feedbackRepository.findAll(pageable);
        return feedbackPage.toList();
    }

    @Override
    public List<Feedback> findAllByCustomer(int customerId, int page, int size) {
        return feedbackRepository.getFeedbacksByCustomerId(customerId, PageRequest.of(page, size));
    }


    @Override
    public List<Feedback> findAllByConsultant(int consultantId, int page, int size) {


        return feedbackRepository.getFeedbacksByConsultantId(consultantId, PageRequest.of(page, size));
    }

}

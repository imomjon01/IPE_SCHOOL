package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.QuestionReq;
import ipe.school.ipe_school.models.dtos.res.QuestionRes;
import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.models.entity.Question;
import ipe.school.ipe_school.models.repo.AttachmentRepository;
import ipe.school.ipe_school.models.repo.QuestionRepository;
import ipe.school.ipe_school.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public QuestionRes save(QuestionReq questionReq) {
        Attachment attachment = attachmentRepository.findById(questionReq.getAttachmentId()).orElseThrow();
        Question saved = questionRepository.save(new Question(attachment, questionReq.getQuestionTest(), questionReq.getVariant(), questionReq.getCurrentAnswer()));
        return new QuestionRes(saved.getId(), saved.getAttachment().getId(), saved.getQuestionTest(), saved.getVariant(), saved.getCurrentAnswer());
    }

    @Override
    public void delete(Long questionId) {
        questionRepository.updateActive(questionId, false);
    }
}

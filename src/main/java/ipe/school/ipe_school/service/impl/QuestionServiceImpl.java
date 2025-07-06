package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.QuestionReq;
import ipe.school.ipe_school.models.dtos.res.QuestionRes;
import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.models.entity.Question;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.models.repo.AttachmentRepository;
import ipe.school.ipe_school.models.repo.QuestionRepository;
import ipe.school.ipe_school.models.repo.TaskRepository;
import ipe.school.ipe_school.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;

    @SneakyThrows
    @Override
    @Transactional
    public QuestionRes save(QuestionReq questionReq) {
        Question question=new Question();
        if (questionReq.getAttachment()!=null) {
            Attachment attachment = new Attachment();
            attachment.setContentType(questionReq.getAttachment().getContentType());
            attachment.setContent(questionReq.getAttachment().getBytes());
            attachment.setActive(true);
            Attachment save = attachmentRepository.save(attachment);
            question.setAttachment(save);
        }
        question.setQuestionTest(questionReq.getQuestionTest());
        question.setVariant(questionReq.getVariant());
        question.setCurrentAnswer(questionReq.getCurrentAnswer());
        question.setActive(true);
        Question saved = questionRepository.save(question);
        addQuestionToCurrentTask(questionReq.getTaskId(),saved);
        if (saved.getAttachment()!=null) {
            return new QuestionRes(saved.getId(), saved.getAttachment().getId(), saved.getQuestionTest(), saved.getVariant(), saved.getCurrentAnswer());
        }
        return  new QuestionRes(saved.getId(), null, saved.getQuestionTest(), saved.getVariant(), saved.getCurrentAnswer());
    }

    private void addQuestionToCurrentTask(Long taskId, Question saved) {
        Task task = taskRepository.findById(taskId).orElseThrow(RuntimeException::new);
        if (task.getQuestions()==null){
            task.setQuestions(new ArrayList<>(List.of(saved)));
        }else {
            task.getQuestions().add(saved);
        }
    }

    @Override
    public void delete(Long questionId) {
        questionRepository.updateActive(questionId, false);
    }

    @SneakyThrows
    @Override
    public QuestionRes updateQuestionBYId(Long questionId, QuestionReq questionReq) {
        Question question = questionRepository.findById(questionId).orElseThrow(RuntimeException::new);
        question.setQuestionTest(questionReq.getQuestionTest());
        question.setVariant(questionReq.getVariant());
        question.setCurrentAnswer(questionReq.getCurrentAnswer());
        if (questionReq.getAttachment()!=null) {
            if (question.getAttachment()!=null) {
                Attachment attachment = question.getAttachment();
                attachment.setContentType(questionReq.getAttachment().getContentType());
                attachment.setContent(questionReq.getAttachment().getBytes());
                attachment.setActive(true);
                Attachment save = attachmentRepository.save(attachment);
                question.setAttachment(save);
            }else {
                Attachment attachment = new Attachment();
                attachment.setContentType(questionReq.getAttachment().getContentType());
                attachment.setContent(questionReq.getAttachment().getBytes());
                attachment.setActive(true);
                Attachment save = attachmentRepository.save(attachment);
                question.setAttachment(save);
            }
        }
        question.setActive(true);
        Question saved = questionRepository.save(question);
        if (saved.getAttachment()!=null) {
            return new QuestionRes(saved.getId(), saved.getAttachment().getId(), saved.getQuestionTest(), saved.getVariant(), saved.getCurrentAnswer());
        }
        return new QuestionRes(saved.getId(), null, saved.getQuestionTest(), saved.getVariant(), saved.getCurrentAnswer());
    }

    @Override
    public QuestionRes getQuestionById(Long questionId) {
        Question question=questionRepository.findById(questionId).orElseThrow(RuntimeException::new);
        if (question.getAttachment()!=null) {
            return new QuestionRes(question.getId(), question.getAttachment().getId(), question.getQuestionTest(), question.getVariant(), question.getCurrentAnswer());
        }
        return new QuestionRes(question.getId(), null, question.getQuestionTest(), question.getVariant(), question.getCurrentAnswer());
    }
}

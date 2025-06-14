package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.AnswerSubmissionReq;
import ipe.school.ipe_school.models.dtos.res.StudentProcessRes;
import ipe.school.ipe_school.models.entity.AnswerSubmission;
import ipe.school.ipe_school.models.entity.Question;
import ipe.school.ipe_school.models.entity.StudentProgress;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.AnswerSubmissionRepository;
import ipe.school.ipe_school.models.repo.QuestionRepository;
import ipe.school.ipe_school.models.repo.StudentProgressRepository;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.AnswerSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerSubmissionServiceImpl implements AnswerSubmissionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerSubmissionRepository answerSubmissionRepository;
    private final StudentProgressRepository studentProgressRepository;

    @Override
    @Transactional
    public StudentProcessRes submitAnswer(User user, List<AnswerSubmissionReq> answerSubmissionReqs) {
        User findUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        List<AnswerSubmission> allAnswers = new ArrayList<>();
        for (AnswerSubmissionReq item : answerSubmissionReqs) {
            AnswerSubmission answerSubmission = new AnswerSubmission();
            answerSubmission.setStudent(findUser);
            Question question = questionRepository.findById(item.getQuestionId()).orElse(null);
            answerSubmission.setQuestion(question);
            answerSubmission.setSelectedAnswer(item.getSelectedAnswer());
            answerSubmission.setCorrect(question.getCurrentAnswer().equals(item.getSelectedAnswer()));
            answerSubmission.setActive(true);
            allAnswers.add(answerSubmission);
        }
        List<AnswerSubmission> savedAnswers = answerSubmissionRepository.saveAll(allAnswers);
        List<AnswerSubmission> correctedAnswer = savedAnswers.stream().filter(item -> item.getCorrect() == true).toList();
        StudentProgress studentProgress = new StudentProgress();
        studentProgress.setStudent(findUser);
        studentProgress.setActive(true);
        studentProgress.setProgress((double) (100*correctedAnswer.size()/savedAnswers.size()));
        StudentProgress save = studentProgressRepository.save(studentProgress);
        return new StudentProcessRes(save.getProgress());
    }
}

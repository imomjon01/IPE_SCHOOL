package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.AnswerSubmissionReq;
import ipe.school.ipe_school.models.dtos.res.StudentProcessRes;
import ipe.school.ipe_school.models.entity.AnswerSubmission;
import ipe.school.ipe_school.models.entity.Question;
import ipe.school.ipe_school.models.entity.StudentProgress;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.*;
import ipe.school.ipe_school.service.interfaces.AnswerSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerSubmissionServiceImpl implements AnswerSubmissionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerSubmissionRepository answerSubmissionRepository;
    private final StudentProgressRepository studentProgressRepository;
    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public StudentProcessRes submitAnswer(User user, List<AnswerSubmissionReq> answerSubmissionReqs) {
        User findUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        Optional<StudentProgress> byStudentId = studentProgressRepository.findByStudentId(findUser.getId());
        StudentProgress studentProgress = byStudentId.orElseGet(() -> StudentProgress.builder()
                .student(findUser)
                .groupName(groupRepository.findGroupNameByStudentId(findUser.getId()))
                .build());

        List<AnswerSubmission> allAnswers = new ArrayList<>();
        for (AnswerSubmissionReq item : answerSubmissionReqs) {
            Optional<AnswerSubmission> existingSubmission = answerSubmissionRepository
                    .findByStudentIdAndQuestionId(findUser.getId(), item.getQuestionId());
            if (existingSubmission.isPresent()) {
                continue;
            }

            AnswerSubmission answerSubmission = new AnswerSubmission();
            answerSubmission.setStudent(findUser);
            Question question = questionRepository.findById(item.getQuestionId()).orElse(null);
            if (question == null) continue;

            answerSubmission.setQuestion(question);
            answerSubmission.setSelectedAnswer(item.getSelectedAnswer());
            boolean isCorrect = question.getCurrentAnswer().equals(item.getSelectedAnswer());
            answerSubmission.setCorrect(isCorrect);

            if (studentProgress.getTotalQuery() == null) {
                studentProgress.setTotalQuery(0);
                studentProgress.setFailedQuery(0);
                studentProgress.setPassedQuery(0);
            }
            Integer totalQuery = studentProgress.getTotalQuery();
            studentProgress.setTotalQuery(totalQuery + 1);
            studentProgress.setPassedQuery(studentProgress.getPassedQuery() + (isCorrect ? 1 : 0));
            studentProgress.setFailedQuery(studentProgress.getFailedQuery() + (isCorrect ? 0 : 1));

            allAnswers.add(answerSubmission);
        }

        if (!allAnswers.isEmpty()) {
            answerSubmissionRepository.saveAll(allAnswers);
            studentProgressRepository.save(studentProgress);
        }

        return new StudentProcessRes(
                findUser.getFullName(),
                studentProgress.getGroupName(),
                studentProgress.getTotalQuery(),
                studentProgress.getPassedQuery(),
                studentProgress.getFailedQuery()
        );
    }

}

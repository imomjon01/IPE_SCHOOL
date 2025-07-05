package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.AnswerSubmissionReq;
import ipe.school.ipe_school.models.dtos.res.StudentProcessRes;
import ipe.school.ipe_school.models.entity.*;
import ipe.school.ipe_school.models.repo.*;
import ipe.school.ipe_school.service.interfaces.AnswerSubmissionService;
import ipe.school.ipe_school.service.interfaces.TaskService;
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
    private final TaskService taskService;

    @Override
    @Transactional
    public StudentProcessRes submitAnswer(User user, List<AnswerSubmissionReq> answerSubmissionReqs) {
        User findUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        Optional<StudentProgress> byStudentId = studentProgressRepository.findByStudentId(findUser.getId());
        StudentProgress studentProgress = byStudentId.orElseGet(() -> StudentProgress.builder()
                .student(findUser)
                .groupName(groupRepository.findGroupNameByStudentId(findUser.getId()))
                .build());

        studentProgressRepository.save(studentProgress);

        Optional<Group> guruh = groupRepository.findById(
                groupRepository.findGroupIdByStudentId(findUser.getId())
        );

        guruh.ifPresent(g -> {
            if (!g.getStudentProgresses().contains(studentProgress)) {
                g.getStudentProgresses().add(studentProgress);
                groupRepository.save(g);
            }
        });

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

    @Override
    public StudentProcessRes results(User user, Long taskId) {
        Task byActiveTask = taskService.findByActiveTask(taskId);
        if (byActiveTask==null){
            return new StudentProcessRes();
        }
        List<AnswerSubmission> submissions = answerSubmissionRepository
                .findByStudentIdAndQuestionIn(user.getId(), taskService.findByActiveTask(taskId).getQuestions());

        Optional<StudentProgress> studentProgress = studentProgressRepository.findByStudentId(user.getId());

        StudentProgress progress = studentProgress.orElseGet(() -> StudentProgress.builder()
                .student(user)
                .groupName(groupRepository.findGroupNameByStudentId(user.getId()))
                .totalQuery(0)
                .passedQuery(0)
                .failedQuery(0)
                .build());

        int totalQuery = submissions.size();
        int passedQuery = (int) submissions.stream().filter(AnswerSubmission::getCorrect).count();
        int failedQuery = totalQuery - passedQuery;

        return new StudentProcessRes(
                user.getFullName(),
                progress.getGroupName(),
                totalQuery,
                passedQuery,
                failedQuery
        );
    }
}

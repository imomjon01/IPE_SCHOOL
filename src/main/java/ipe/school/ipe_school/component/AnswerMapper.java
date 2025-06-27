package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.dtos.res.TopStudentByGroupRes;
import ipe.school.ipe_school.models.entity.StudentProgress;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.AnswerSubmissionRepository;
import ipe.school.ipe_school.models.repo.StudentProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AnswerMapper {

    private final StudentProgressRepository studentProgressRepository;

    public List<TopStudentByGroupRes> users(List<User> users) {
        return users.stream()
                .map(user -> {
                    StudentProgress progress = studentProgressRepository.findByStudent(user);
                    return TopStudentByGroupRes.builder()
                            .studentId(user.getId())
                            .fullName(user.getFullName())
                            .groupName(progress.getGroupName())
                            .totalQuery(progress.getTotalQuery())
                            .passedQuery(progress.getPassedQuery())
                            .failedQuery(progress.getFailedQuery())
                            .build();
                })
                .toList();
    }
}


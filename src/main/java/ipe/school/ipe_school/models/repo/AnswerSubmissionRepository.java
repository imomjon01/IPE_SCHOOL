package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.AnswerSubmission;
import ipe.school.ipe_school.models.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnswerSubmissionRepository extends JpaRepository<AnswerSubmission, Long> {
    Optional<AnswerSubmission> findByStudentIdAndQuestionId(Long studentId, Long questionId);
    List<AnswerSubmission> findByStudentIdAndQuestionIn(Long studentId, List<Question> questions);

}
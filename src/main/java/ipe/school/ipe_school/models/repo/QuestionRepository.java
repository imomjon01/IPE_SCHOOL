package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByIdIn(List<Long> ids);

    @Query("UPDATE Question q set q.active =:active where q.id=:questionId")
    void updateActive(Long questionId, boolean active);
}
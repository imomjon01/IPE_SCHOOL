package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
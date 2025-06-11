package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.StudentProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProgressRepository extends JpaRepository<StudentProgress, Long> {
}
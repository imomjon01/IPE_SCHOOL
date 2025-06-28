package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.StudentProgress;
import ipe.school.ipe_school.models.entity.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentProgressRepository extends JpaRepository<StudentProgress, Long> {

    Optional<StudentProgress> findByStudentId(Long studentId);

    @Query(value = "SELECT * FROM student_progress ORDER BY passed_query DESC LIMIT 10", nativeQuery = true)
    List<StudentProgress> findTop10Students();


    StudentProgress findByStudent(User user);
}
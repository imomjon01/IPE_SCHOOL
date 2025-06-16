package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t from Task t where t.id = :taskId and t.active = :active")
    Task findByIdActive(Long taskId, boolean active);


}
package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByIdActive(Long taskId, boolean b);
}
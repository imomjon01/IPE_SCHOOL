package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByModuleAnd_active(Module module,boolean active);
}
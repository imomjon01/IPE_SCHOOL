package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findAllByActive(Boolean active);

    @Query("SELECT m from Module m where m.id=:moduleId")
    Module findAllByModuleId(Long moduleId);
}
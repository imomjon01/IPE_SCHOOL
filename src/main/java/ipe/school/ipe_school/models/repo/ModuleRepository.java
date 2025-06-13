package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findAllBy_active(boolean active);
}
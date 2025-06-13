package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String roleMentor);
    List<Roles> findByIdIn(List<Long> ids);
}
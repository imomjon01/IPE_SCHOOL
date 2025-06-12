package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.dtos.res.GroupDetailsRes;
import ipe.school.ipe_school.models.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllBy_active(boolean active);

    Group getGroupById(Long id);
}
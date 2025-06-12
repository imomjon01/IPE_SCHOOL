package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllBy_active(boolean active);

    Group getGroupById(Long id);

    List<Group> findByMentorId(Long mentorId);

    @Query("SELECT u FROM User u JOIN u.roles r " +
            "WHERE r.name = 'ROLE_STUDENT' AND u._active = :isActive")
    Page<Group> findAllActiveGroups(@Param("isActive") Boolean isActive, Pageable pageable);

    @Query("SELECT g FROM Group g  " +
            "WHERE g._active = :isActive " +
            "AND (" +
            "LOWER(g.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            ")")
    Page<Group> findAllActiveGroupsWithSearch(String search, boolean b, Pageable pageable);

}
package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByActive(Boolean active);

    Group getGroupById(Long id);

    @Query("SELECT g from Group g where g.active = true and g.mentor.id = :mentorId ")
    List<Group> findByMentorId(Long mentorId);

    @Query("SELECT g FROM Group g where g.active = :isActive ")
    Page<Group> findAllActiveGroups(@Param("isActive") Boolean isActive, Pageable pageable);

    @Query("SELECT g FROM Group g  " +
            "WHERE g.active = :isActive " +
            "AND (" +
            "LOWER(g.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            ")")
    Page<Group> findAllActiveGroupsWithSearch(String search, boolean isActive, Pageable pageable);

    @Query("SELECT g.id FROM Group g JOIN g.students s WHERE s.id = :studentId")
    Long findGroupIdByStudentId(@Param("studentId") Long studentId);


}
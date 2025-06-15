package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneNumber(String phoneNumber);

    List<User> findByActiveTrueAndRoles_Name(String roleName);


    @Query("SELECT u FROM User u JOIN u.roles r " +
            "WHERE r.name = 'ROLE_STUDENT' AND u.active = :isActive " +
            "AND (" +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%'))" +
            ")")
    Page<User> findAllActiveStudentsWithSearch(@Param("search") String search,
                                               @Param("isActive") Boolean isActive,
                                               Pageable pageable);


    @Query("SELECT u FROM User u JOIN u.roles r " +
            "WHERE r.name = 'ROLE_STUDENT' AND u.active = :isActive")
    Page<User> findAllActiveStudents(@Param("isActive") Boolean isActive, Pageable pageable);


    List<User> findUsersByActive(boolean active);

    List<User> findByRoles(List<Roles> roles);

    @Query("SELECT g from Group g where g.active = :active and LOWER(g.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<User> findAllActiveGroupsWithSearch(String search, boolean active, Pageable pageable);

    @Query("SELECT g from Group g where g.active = :active")
    Page<User> findAllActiveMentors(boolean active, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_STUDENT' AND u.active = true")
    List<User> findAllActiveStudents();



}

package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneNumber(String phoneNumber);

    List<User> findBy_activeTrueAndRoles_Name(String roleName);

}

package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByPhoneNumber(String phoneNumber);
}
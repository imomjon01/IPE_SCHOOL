package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);
    List<User> findAll();
}

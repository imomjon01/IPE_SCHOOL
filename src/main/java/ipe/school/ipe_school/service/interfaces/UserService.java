package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.models.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);
    List<User> findAll();

    List<UserRes> findByRole(Long roleId);

    UserRes changeUserRole(Long userId, List<Long> roleIds);
}

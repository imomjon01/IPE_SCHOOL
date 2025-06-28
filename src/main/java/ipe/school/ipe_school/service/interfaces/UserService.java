package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    User save(User user);

    List<UserRes> findByRole(Long roleId);

    UserRes changeUserRole(Long userId, List<Long> roleIds);

    Page<UserRes> findAllUsersActive(int page, int size, String search, Boolean isActive);

    void updateUser_Active(Long userId);

    UserRes restoration(Long userId);

    User getAuthenticatedUser(Authentication authentication);
}

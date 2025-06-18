package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.RolesRepository;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    @Transactional
    public List<UserRes> findByRole(Long roleId) {
        Roles roles = rolesRepository.findById(roleId).orElseThrow(RuntimeException::new);
        List<User> users = userRepository.findByRoles(new ArrayList<>(List.of(roles)));
        return users.stream().map(user -> new UserRes(user.getId(),
                user.getFirstName(), user.getLastName(),
                user.getPhoneNumber(), user.getRoles().stream().map(Roles::getName).toList())).toList();
    }

    @Override

    @Transactional
    public UserRes changeUserRole(Long userId, List<Long> roleIds) {
        List<Roles> roles = rolesRepository.findByIdIn(roleIds);
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        user.setRoles(roles);
        return new UserRes(user.getId(), user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(),
                user.getRoles().stream().map(Roles::getName).toList());
    }

    @Override
    public Page<UserRes> findAllUsersActive(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage;

        if (search != null && !search.trim().isEmpty()) {
            usersPage = userRepository.findAllActiveUsersWithSearch(search, true, pageable);
        } else {
            usersPage = userRepository.findAllActiveUsers(true, pageable);
        }

        return usersPage.map(user -> new UserRes(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getRoles().stream().map(Roles::getName).toList()
        ));
    }
}

package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.entity.StudentProgress;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.RolesRepository;
import ipe.school.ipe_school.models.repo.StudentProgressRepository;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final StudentProgressRepository studentProgressRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
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
    public Page<UserRes> findAllUsersActive(int page, int size, String search, Boolean isActive) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<User> usersPage;

        if (search != null && !search.trim().isEmpty()) {
            usersPage = userRepository.findAllActiveUsersWithSearch(search, isActive, pageable);
        } else {
            usersPage = userRepository.findAllActiveUsers(isActive, pageable);
        }

        return usersPage.map(user -> new UserRes(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getRoles().stream().map(Roles::getName).toList()
        ));
    }

    @Transactional
    @Override
    public void updateUser_Active(Long userId) {
        User user = userRepository.findById(userId).get();
        user.setActive(false);
        Optional<StudentProgress> byStudentId = studentProgressRepository.findByStudentId(user.getId());
        if (byStudentId.isPresent()) {
            byStudentId.get().setActive(false);
            studentProgressRepository.save(byStudentId.get());
        }
        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserRes restoration(Long userId) {
        User user = userRepository.findById(userId).get();
        if (!user.getActive()) {
            user.setActive(true);
            userRepository.save(user);
            Optional<StudentProgress> byStudentId = studentProgressRepository.findByStudentId(userId);
            if (byStudentId.isPresent()) {
                byStudentId.get().setActive(true);
                studentProgressRepository.save(byStudentId.get());
            }
        }
        return new UserRes(user.getId(), user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(),
                user.getRoles().stream().map(Roles::getName).toList());
    }

    @Override
    public User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("e: ", new AccessDeniedException("User is not authenticated"));
        }

        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByPhoneNumber(username);
    }
}

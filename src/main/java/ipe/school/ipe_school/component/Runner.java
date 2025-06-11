package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.entity.enums.Role;
import ipe.school.ipe_school.service.impl.RolesServiceImpl;
import ipe.school.ipe_school.service.interfaces.RolesService;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final RolesService rolesService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public void run(String... args) throws Exception {
        if (rolesService.findAll().isEmpty()) {
            rolesService.saveAll(new ArrayList<>(List.of(
                    new Roles("ROLE_ADMIN"),
                    new Roles("ROLE_MENTOR"),
                    new Roles("ROLE_STUDENT")
            )));
        }
    }
}


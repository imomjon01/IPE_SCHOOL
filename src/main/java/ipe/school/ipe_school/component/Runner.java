package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.dtos.req.MentorReq;
import ipe.school.ipe_school.models.dtos.res.MentorRes;
import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.entity.enums.Role;
import ipe.school.ipe_school.service.impl.MentorServiceImpl;
import ipe.school.ipe_school.service.impl.RolesServiceImpl;
import ipe.school.ipe_school.service.interfaces.MentorService;
import ipe.school.ipe_school.service.interfaces.RolesService;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final RolesService rolesService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MentorService mentorService;


    public void run(String... args) throws Exception {
        if (rolesService.findAll().isEmpty()) {
            rolesService.saveAll(new ArrayList<>(List.of(
                    new Roles("ROLE_ADMIN"),
                    new Roles("ROLE_MENTOR"),
                    new Roles("ROLE_STUDENT")
            )));
        }

//        MentorReq mentorReq = new MentorReq("Eshmant", "Toshmatov", "+998 99 999 99 99", "root123");
//        MentorRes mentor = mentorService.createMentor(mentorReq);
//        System.out.println("Mentor: " + mentor);

    }
}


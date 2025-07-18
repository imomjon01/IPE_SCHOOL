package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.dtos.req.MentorReq;
import ipe.school.ipe_school.models.dtos.res.MentorRes;
import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.MentorService;
import ipe.school.ipe_school.service.interfaces.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final RolesService rolesService;
    private final MentorService mentorService;
    private final UserRepository userRepository;

    public void run(String... args) throws Exception {
        if (rolesService.findAll().isEmpty()) {
            rolesService.saveAll(new ArrayList<>(List.of(
                    new Roles("ROLE_ADMIN"),
                    new Roles("ROLE_MENTOR"),
                    new Roles("ROLE_STUDENT"),
                    new Roles("ROLE_SUPER_MENTOR")
            )));
        }
        if (userRepository.findAll().isEmpty()) {
            MentorReq mentorReq = new MentorReq("Imomjon", "Risqiboyev", "+99811", "root123");
            MentorRes mentor = mentorService.createMentor(mentorReq);
            System.out.println("Mentor: " + mentor);
        }
    }
}


package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.dtos.req.MentorReq;
import ipe.school.ipe_school.models.dtos.res.MentorRes;
import ipe.school.ipe_school.models.entity.Roles;
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

    public void run(String... args) throws Exception {
        if (rolesService.findAll().isEmpty()) {
            rolesService.saveAll(new ArrayList<>(List.of(
                    new Roles("ROLE_ADMIN"),
                    new Roles("ROLE_MENTOR"),
                    new Roles("ROLE_STUDENT")
            )));
        }
/*        MentorReq mentorReq = new MentorReq("Eshmant", "Toshmatov", "1234", "123");
        MentorRes mentor = mentorService.createMentor(mentorReq);
        System.out.println("Mentor: " + mentor);*/
    }
}


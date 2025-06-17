package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.service.interfaces.GroupService;
import ipe.school.ipe_school.service.interfaces.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + MENTOR)
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;
    private final GroupService groupService;

    @GetMapping(GROUP)
    public ResponseEntity<List<GroupRes>> getGroupByMentor(@AuthenticationPrincipal User user) {
        List<GroupRes> mentorsGroup=groupService.getMentorGroups(user);
        return new ResponseEntity<>(mentorsGroup, HttpStatus.OK);
    }
}

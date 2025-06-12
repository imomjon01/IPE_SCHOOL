package ipe.school.ipe_school.controller;

import ipe.school.ipe_school.models.dtos.req.MentorReq;
import ipe.school.ipe_school.models.dtos.req.MentorUpdateReq;
import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.dtos.res.MentorRes;
import ipe.school.ipe_school.service.interfaces.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RequestMapping(API_PATH + API_VERSION + ADMIN + MENTOR)
@RestController
@RequiredArgsConstructor
public class AdminMentorController {

    private final MentorService mentorService;

    @DeleteMapping("/{mentor_id}")
    public ResponseEntity<GroupRes> delete_mentor(@PathVariable Long mentor_id) {
        mentorService.updateMentor_Active(mentor_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(ADD)
    public ResponseEntity<MentorRes> addMentor(@RequestBody MentorReq mentorReq) {
        MentorRes mentorRes = mentorService.createMentor(mentorReq);
        return new ResponseEntity<>(mentorRes, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<MentorRes>> getAllMentors() {
        List<MentorRes> mentorRes = mentorService.getMentorsBy_Active();
        return new ResponseEntity<>(mentorRes, HttpStatus.OK);
    }

    @GetMapping("/{mentor_id}")
    public ResponseEntity<MentorRes> getMentorById(@PathVariable Long mentor_id) {
        MentorRes mentorRes = mentorService.getMentorId(mentor_id);
        return new ResponseEntity<>(mentorRes, HttpStatus.OK);
    }

    @PostMapping("/{mentor_id}")
    public ResponseEntity<MentorRes> updateMentor(@PathVariable Long mentor_id, @RequestBody MentorUpdateReq mentorUpdateReq) {
        MentorRes mentorRes = mentorService.updateMentor(mentor_id, mentorUpdateReq);
        return new ResponseEntity<>(mentorRes, HttpStatus.OK);
    }
}

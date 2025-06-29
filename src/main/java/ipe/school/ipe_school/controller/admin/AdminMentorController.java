package ipe.school.ipe_school.controller.admin;

import ipe.school.ipe_school.models.dtos.req.MentorReq;
import ipe.school.ipe_school.models.dtos.req.MentorUpdateReq;
import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.dtos.res.MentorRes;
import ipe.school.ipe_school.service.interfaces.GroupService;
import ipe.school.ipe_school.service.interfaces.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final GroupService groupService;

    @DeleteMapping("/{mentorId}")
    public ResponseEntity<GroupRes> delete_mentor(@PathVariable Long mentorId) {
        mentorService.updateMentor_Active(mentorId);
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

    @GetMapping("/{mentorId}")
    public ResponseEntity<MentorRes> getMentorById(@PathVariable Long mentorId) {
        MentorRes mentorRes = mentorService.getMentorId(mentorId);
        return new ResponseEntity<>(mentorRes, HttpStatus.OK);
    }

    @PostMapping("/{mentorId}")
    public ResponseEntity<MentorRes> updateMentor(@PathVariable Long mentorId, @RequestBody MentorUpdateReq mentorUpdateReq) {
        MentorRes mentorRes = mentorService.updateMentor(mentorId, mentorUpdateReq);
        return new ResponseEntity<>(mentorRes, HttpStatus.OK);
    }

    @GetMapping("/getMentors")
    public ResponseEntity<Page<MentorRes>> getStudentsActiveFalse(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(required = false) String search
    ) {
        Page<MentorRes> mentorRes = mentorService.getAllMentorsActive(page, size, search);
        return ResponseEntity.ok(mentorRes);
    }

    @PutMapping("/updateGroup/{groupId}")
    public ResponseEntity<MentorRes> updateGroupMentor(@PathVariable Long groupId, @RequestParam(required = false) Long mentorId) {
        groupService.updateMentor(groupId,mentorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getMentorCount")
    public ResponseEntity<Integer> countMentors() {
        Integer totalElements = mentorService.getMentorCount();
        return new ResponseEntity<>(totalElements, HttpStatus.OK);
    }
}
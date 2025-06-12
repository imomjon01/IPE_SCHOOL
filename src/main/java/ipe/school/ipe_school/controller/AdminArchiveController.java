package ipe.school.ipe_school.controller;

import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.service.interfaces.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequiredArgsConstructor

@RequestMapping(API_PATH + API_VERSION + ADMIN + ARCHIVE)
public class AdminArchiveController {
    private final ArchiveService archiveService;

    @GetMapping(STUDENT)
    public ResponseEntity<Page<StudentDetailsRes>> getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(required = false) String search
    ) {
        Page<StudentDetailsRes> students = archiveService.findAllStudents_isActive(page, size, search);
        return ResponseEntity.ok(students);
    }

    @GetMapping(GROUP)
    public ResponseEntity<Page<GroupRes>> getGroups(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(required = false) String search
    ) {
        Page<GroupRes> students = archiveService.findAllGroups_isActive(page, size, search);
        return ResponseEntity.ok(students);
    }

    @GetMapping(MENTOR)
    public ResponseEntity<Page<StudentDetailsRes>> getMentors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(required = false) String search
    ) {
        Page<StudentDetailsRes> students = archiveService.findAllStudents_isActive(page, size, search);
        return ResponseEntity.ok(students);
    }

}

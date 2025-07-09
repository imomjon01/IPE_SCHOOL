package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.res.TopStudentByGroupRes;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.service.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + STUDENT)
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/levels")
    public ResponseEntity<List<TopStudentByGroupRes>> getLevels(@AuthenticationPrincipal User user) {
        List<TopStudentByGroupRes> studentRes = studentService.getTopStudentGroups(user);
        return new ResponseEntity<>(studentRes, HttpStatus.OK);
    }
}

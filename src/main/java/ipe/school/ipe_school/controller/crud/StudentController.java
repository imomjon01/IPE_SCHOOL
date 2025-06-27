package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.req.StudentDto;
import ipe.school.ipe_school.models.dtos.res.StudentRes;
import ipe.school.ipe_school.models.dtos.res.TopStudentByGroupRes;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + STUDENT)
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/levels")
    public ResponseEntity<List<TopStudentByGroupRes>> getLevels(@AuthenticationPrincipal User user) {
        User byUser = studentService.findByUser(user);
        List<TopStudentByGroupRes> studentRes = studentService.getTopStudentGroups(byUser);
        return new ResponseEntity<>(studentRes, HttpStatus.OK);
    }
}

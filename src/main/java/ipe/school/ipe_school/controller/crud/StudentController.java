package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.req.StudentDto;
import ipe.school.ipe_school.models.dtos.res.StudentRes;
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
    public ResponseEntity<?> getLevels(@AuthenticationPrincipal User user) {
        System.out.println(user.getPhoneNumber());
        return ResponseEntity.ok("");
    }


    @PutMapping
    public ResponseEntity<StudentRes> updateStudent(
            @AuthenticationPrincipal User user,
            @RequestPart("studentDto") StudentDto studentDto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        if (user.getPhoneNumber() == null) {
            System.err.println("Xatolik bo'ldi");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User findUser = studentService.findByUser(user);
        if (findUser == null) {
            System.err.println("Xatolik bo'ldi findUserda");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        StudentRes studentRes = studentService.updateStudent(findUser.getId(), studentDto, imageFile);
        return ResponseEntity.ok(studentRes);
    }

}

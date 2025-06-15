package ipe.school.ipe_school.controller.admin;

import ipe.school.ipe_school.models.dtos.req.StudentDto;
import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.models.dtos.res.StudentRes;
import ipe.school.ipe_school.service.interfaces.StudentService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import static ipe.school.ipe_school.utils.ApiConstants.*;

@MultipartConfig
@RestController
@RequestMapping(API_PATH + API_VERSION + ADMIN + STUDENT)
@RequiredArgsConstructor
public class AdminStudentController {
    private final StudentService studentService;

    @GetMapping()
    public ResponseEntity<Page<StudentDetailsRes>> getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(required = false) String search
    ) {
        Page<StudentDetailsRes> students = studentService.findAllStudents_isActive(page, size, search);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/save")
    public ResponseEntity<StudentRes> saveStudent(@RequestBody StudentDto studentDto) {
        System.out.println("keldi =================");
        StudentRes studentRes = studentService.save(studentDto);
        return new ResponseEntity<>(studentRes, HttpStatus.CREATED);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<StudentRes> deleteStudent(@PathVariable Long studentId) {
        StudentRes studentRes = studentService.activeUpdate(studentId);
        return new ResponseEntity<>(studentRes, HttpStatus.OK);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentRes> updateStudent(@PathVariable Long studentId,
                                       @RequestBody StudentDto studentDto,
                                       @RequestPart("image") MultipartFile imageFile) {
        StudentRes studentRes = studentService.updateStudent(studentId, studentDto, imageFile);
        return new ResponseEntity<>(studentRes, HttpStatus.OK);

    }

}

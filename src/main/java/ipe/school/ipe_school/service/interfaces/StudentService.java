package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.StudentDto;
import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.models.dtos.res.StudentRes;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    StudentRes save(StudentDto studentDto);

    Page<StudentDetailsRes> findAllStudents_isActive(int page, int size, String search);

    StudentRes activeUpdate(Long studentId);

    StudentRes updateStudent(Long studentId, StudentDto studentDto, MultipartFile imageFile);

    List<StudentDetailsRes> getAllStudents();
}

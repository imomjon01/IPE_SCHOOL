package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.models.dtos.res.StudentRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArchiveService {
    Page<StudentDetailsRes> findAllStudents_isActive(int page, int size, String search);

    Page<GroupRes> findAllGroups_isActive(int page, int size, String search);

    Page<UserRes> findAllMentors_isActive(int page, int size, String search);
}

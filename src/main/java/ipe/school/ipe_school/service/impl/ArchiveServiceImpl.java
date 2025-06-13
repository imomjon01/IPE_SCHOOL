package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.models.entity.Group;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.GroupRepository;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchiveServiceImpl implements ArchiveService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public Page<StudentDetailsRes> findAllStudents_isActive(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> studentsPage;

        if (search != null && !search.trim().isEmpty()) {
            studentsPage = userRepository.findAllActiveStudentsWithSearch(search, false, pageable);
        } else {
            studentsPage = userRepository.findAllActiveStudents(false, pageable);
        }

        return studentsPage.map(user -> new StudentDetailsRes(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber()
        ));
    }

    @Override
    public Page<GroupRes> findAllGroups_isActive(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Group> groupPage;

        if (search != null && !search.trim().isEmpty()) {
            groupPage = groupRepository.findAllActiveGroupsWithSearch(search, false, pageable);
        } else {
            groupPage = groupRepository.findAllActiveGroups(false, pageable);
        }

        return groupPage.map(group -> new GroupRes(
                group.getId(),
                group.getName()
        ));
    }

    @Override
    public Page<UserRes> findAllMentors_isActive(int page, int size, String search) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<User> mentorsPage;
        if (search != null && !search.trim().isEmpty()) {
            mentorsPage = userRepository.findAllActiveGroupsWithSearch(search, false, pageRequest);
        } else {
            mentorsPage = userRepository.findAllActiveMentors(false, pageRequest);
        }
        return mentorsPage.map(mentor -> new UserRes(
                mentor.getId(),
                mentor.getFirstName(),
                mentor.getLastName(),
                mentor.getPhoneNumber()
        ));
    }
}

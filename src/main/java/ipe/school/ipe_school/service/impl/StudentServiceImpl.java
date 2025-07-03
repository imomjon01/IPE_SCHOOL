package ipe.school.ipe_school.service.impl;

import io.jsonwebtoken.lang.Collections;
import ipe.school.ipe_school.component.AnswerMapper;
import ipe.school.ipe_school.models.dtos.req.StudentDto;
import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.models.dtos.res.StudentProcessRes;
import ipe.school.ipe_school.models.dtos.res.StudentRes;
import ipe.school.ipe_school.models.dtos.res.TopStudentByGroupRes;
import ipe.school.ipe_school.models.entity.*;
import ipe.school.ipe_school.models.repo.*;
import ipe.school.ipe_school.service.interfaces.GroupService;
import ipe.school.ipe_school.service.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final AttachmentRepository attachmentRepository;
    private final GroupService groupService;
    private final StudentProgressRepository studentProgressRepository;
    private final GroupRepository groupRepository;
    private final AnswerSubmissionRepository answerSubmissionRepository;
    private final AnswerMapper answerMapper;


    @SneakyThrows
    @Override
    public StudentRes save(StudentDto studentDto) {
        User user = User.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .phoneNumber(studentDto.getPhoneNumber())
                .password(passwordEncoder.encode(studentDto.getPassword()))
                .active(true)
                .attachment(new Attachment())
                .build();

        ClassPathResource imgFile = new ClassPathResource("images/img.png");
        Attachment attachment = new Attachment();
        try (InputStream inputStream = imgFile.getInputStream()) {
            byte[] imageBytes = inputStream.readAllBytes();
            attachment.setContent(imageBytes);
        }
        String contentType = ".png";
        attachment.setContentType(contentType);
        Attachment save1 = attachmentRepository.save(attachment);
        user.setAttachment(save1);
        Optional<Roles> byId = rolesRepository.findById(3L);
        List<Roles> roles = new ArrayList<>(List.of(byId.get()));
        user.setRoles(roles);
        User saved = userRepository.save(user);
        return new StudentRes(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getPhoneNumber(), saved.getPassword(), saved.getAttachment().getContent());
    }

    @Transactional
    @Override
    public Page<StudentDetailsRes> findAllStudents_isActive(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> studentsPage;

        if (search != null && !search.trim().isEmpty()) {
            studentsPage = userRepository.findAllActiveStudentsWithSearch(search, true, pageable);
        } else {
            studentsPage = userRepository.findAllActiveStudents(true, pageable);
        }

        return studentsPage.map(user -> new StudentDetailsRes(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                groupService.getGroupByStudentId(user.getId()).orElse(null)
        ));
    }

    @Override
    public StudentRes activeUpdate(Long studentId) {
        User user = userRepository.findById(studentId).orElseThrow();
        user.setActive(false);
        return new StudentRes(user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getPassword(), user.getAttachment().getContent());
    }

    @SneakyThrows
    @Override
    public StudentRes updateStudent(Long studentId, StudentDto studentDto, MultipartFile imageFile) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (studentDto.getFirstName() != null) {
            user.setFirstName(studentDto.getFirstName());
        }
        if (studentDto.getLastName() != null) {
            user.setLastName(studentDto.getLastName());
        }
        if (studentDto.getPhoneNumber() != null) {
            user.setPhoneNumber(studentDto.getPhoneNumber());
        }
        if (studentDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Attachment attachment = new Attachment();
                attachment.setContentType(imageFile.getContentType());
                attachment.setContent(imageFile.getBytes());

                attachment = attachmentRepository.save(attachment);

                user.setAttachment(attachment);
            } catch (IOException e) {
                throw new RuntimeException("Faylni o‘qishda xatolik yuz berdi", e);
            }
        }

        user = userRepository.save(user);

        return new StudentRes(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getAttachment().getContent()
        );
    }

    @Override
    public List<StudentDetailsRes> getAllStudents() {
        List<User> users = userRepository.findAllActiveStudents();
        List<StudentDetailsRes> studentDetailsRes = new ArrayList<>();
        for (User user : users) {
            studentDetailsRes.add(new StudentDetailsRes(user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), groupService.getGroupByStudentId(user.getId()).orElse(null)));
        }
        return studentDetailsRes;
    }

    @Override
    public List<StudentProcessRes> getTop10StudentProgress() {
        List<StudentProgress> studentProgresses = studentProgressRepository.findTop10Students();
        List<StudentProcessRes> studentProcessRes = new ArrayList<>();
        for (StudentProgress studentProgress : studentProgresses) {
            studentProcessRes.add(new StudentProcessRes(
                    studentProgress.getStudent().getFullName(),
                    studentProgress.getGroupName(),
                    studentProgress.getPassedQuery(), studentProgress.getPassedQuery(), studentProgress.getPassedQuery()
            ));
        }
        return studentProcessRes;
    }

    @Override
    public Integer getStudentsCount() {
        return userRepository.findAllCountStudentActive();
    }

    @Override
    public User findByUser(User user) {
        return userRepository.findByPhoneNumber(user.getPhoneNumber());
    }

    @Override
    public List<TopStudentByGroupRes> getTopStudentGroups(User byUser) {
        User byPhoneNumber = userRepository.findByPhoneNumber(byUser.getPhoneNumber());
        if (byPhoneNumber == null) {
            return Collections.emptyList(); // Foydalanuvchi topilmadi
        }

        Long groupId = groupRepository.findGroupIdByStudentId(byPhoneNumber.getId());
        if (groupId == null) {
            return Collections.emptyList(); // Guruh topilmadi
        }

        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            return Collections.emptyList(); // Guruh mavjud emas
        }

        List<User> students = optionalGroup.get().getStudents();

        if (students == null || students.isEmpty()) {
            return Collections.emptyList(); // Talabalar ro'yxati bo'sh
        }

        return answerMapper.users(students);
    }

}

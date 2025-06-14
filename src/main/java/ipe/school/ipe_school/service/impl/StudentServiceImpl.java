package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.StudentDto;
import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.models.dtos.res.StudentRes;
import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.AttachmentRepository;
import ipe.school.ipe_school.models.repo.GroupRepository;
import ipe.school.ipe_school.models.repo.RolesRepository;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        return new StudentRes(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getPhoneNumber(), saved.getPassword());
    }

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
                user.getPhoneNumber()
        ));
    }

    @Override
    public StudentRes activeUpdate(Long studentId) {
        User user = userRepository.findById(studentId).orElseThrow();
        user.setActive(false);
        return new StudentRes(user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getPassword());
    }

    @SneakyThrows
    @Override
    public StudentRes updateStudent(Long studentId, StudentDto studentDto, MultipartFile imageFile) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(studentDto.getFirstName());
        user.setLastName(studentDto.getLastName());
        user.setPhoneNumber(studentDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(studentDto.getPassword()));

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
                user.getPassword()
        );
    }


}

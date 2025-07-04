package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.*;
import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.dtos.res.MentorRes;
import ipe.school.ipe_school.models.entity.*;
import ipe.school.ipe_school.models.repo.*;
import ipe.school.ipe_school.service.interfaces.GroupService;
import ipe.school.ipe_school.service.interfaces.MentorService;
import lombok.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final AttachmentRepository attachmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupService groupService;
    private final StudentProgressRepository studentProgressRepository;
    private final GroupRepository groupRepository;

    @Transactional
    @Override
    public void updateMentor_Active(Long mentor_id) {
        Optional<User> byId = userRepository.findById(mentor_id);
        byId.get().setActive(false);
        Optional<StudentProgress> byStudentId = studentProgressRepository.findByStudentId(byId.get().getId());
        if (byStudentId.isPresent()) {
            byStudentId.get().setActive(false);
            studentProgressRepository.save(byStudentId.get());
        }
        List<Group> byMentor = groupRepository.findByMentor(byId.get());
        for (Group group : byMentor) {
            group.setMentor(null);
        }
        groupRepository.saveAll(byMentor);
        userRepository.save(byId.get());
    }

    @Transactional
    @SneakyThrows
    @Override
    public MentorRes createMentor(MentorReq mentorReq) {
        User user = new User();
        user.setFirstName(mentorReq.getFirstName());
        user.setLastName(mentorReq.getLastName());
        user.setPassword(passwordEncoder.encode(mentorReq.getPassword()));
        user.setPhoneNumber(mentorReq.getPhoneNumber());
        user.setActive(true);

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
        Optional<Roles> byId = rolesRepository.findById(1L);
        List<Roles> roles = new ArrayList<>(List.of(byId.get()));
        user.setRoles(roles);
        User save = userRepository.save(user);

        return new MentorRes(save.getId(),
                save.getFirstName(), save.getLastName(),
                save.getPhoneNumber(),
                save.getAttachment().getContent(), null);
    }

    @Transactional
    @Override
    public List<MentorRes> getMentorsBy_Active() {
        List<User> users = userRepository.findByActiveTrueAndRoles_Name("ROLE_MENTOR");
        return users.stream().map(user ->
                new MentorRes(user.getId(), user.getFirstName(),
                        user.getLastName(), user.getPhoneNumber(),
                        user.getAttachment().getContent(),
                        groupService.getGroupByMentorId(user.getId())
                                .stream().map(item ->
                                        new GroupRes(item.getId(),
                                                item.getName()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public MentorRes getMentorId(Long mentorId) {
        Optional<User> userOptional = userRepository.findById(mentorId);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        if (user.getActive()) {
            if (user.getRoles().contains(rolesRepository.findByName("ROLE_MENTOR"))){
                List<Group> groups = groupService.getGroupByMentorId(mentorId);
                return new MentorRes(user.getId(), user.getFirstName(),
                        user.getLastName(), user.getPhoneNumber(),
                        user.getAttachment().getContent(),
                        groups.stream().map(item ->
                                new GroupRes(item.getId(),
                                        item.getName())).
                                collect(Collectors.toList()));
            }
        }
        return null;
    }

    @Override
    public MentorRes updateMentor(Long mentorId, MentorUpdateReq mentorUpdateReq) {
        User user = userRepository.findById(mentorId).get();
        user.setActive(mentorUpdateReq.is_active());
        user.setPassword(passwordEncoder.encode(mentorUpdateReq.getPassword()));
        user.setRoles(mentorUpdateReq.getRoles());
        User save = userRepository.save(user);
        return new MentorRes(save.getId(),save.getFirstName(),
                save.getLastName(), save.getPhoneNumber(),
                save.getAttachment().getContent(),
                groupService.getGroupByMentorId(mentorId)
                        .stream().map(item -> new GroupRes
                                (item.getId(), item.getName()))
                        .collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public Page<MentorRes> getAllMentorsActive(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage;

        if (search != null && !search.trim().isEmpty()) {
            usersPage = userRepository.findAllActiveMentorsWithSearch(search, true, pageable);
        } else {
            usersPage = userRepository.findAllActiveMentor(true, pageable);
        }

        List<MentorRes> content = usersPage.stream()
                .map(user -> new MentorRes(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhoneNumber(),
                        null,
                        groupRepository.findByMentor(user).stream()
                                .map(item -> new GroupRes(item.getId(), item.getName()))
                                .toList()
                ))
                .toList();

        return new PageImpl<>(content, pageable, usersPage.getTotalElements());
    }

    @Override
    public Integer getMentorCount() {
        return userRepository.findAllCountMentorActive();
    }
}

package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.*;
import ipe.school.ipe_school.models.dtos.res.MentorRes;
import ipe.school.ipe_school.models.entity.*;
import ipe.school.ipe_school.models.repo.*;
import ipe.school.ipe_school.service.interfaces.GroupService;
import ipe.school.ipe_school.service.interfaces.MentorService;
import lombok.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final AttachmentRepository attachmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupService groupService;

    @Transactional
    @Override
    public void updateMentor_Active(Long mentor_id) {
        Optional<User> byId = userRepository.findById(mentor_id);
        byId.get().set_active(false);
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
        user.set_active(true);

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
        Optional<Roles> byId = rolesRepository.findById(2L);
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
        List<User> users = userRepository.findBy_activeTrueAndRoles_Name("ROLE_MENTOR");
        List<Group> groups = groupService.getAllGroup();

        Map<Long, List<Group>> mentorGroupsMap = new HashMap<>();
        for (Group group : groups) {
            Long mentorId = group.getMentor().getId();
            mentorGroupsMap
                    .computeIfAbsent(mentorId, k -> new ArrayList<>())
                    .add(group);
        }

        List<MentorRes> mentors = new ArrayList<>();
        for (User user : users) {
            List<Group> groupList = mentorGroupsMap.getOrDefault(user.getId(), new ArrayList<>());
            MentorRes mentorRes = new MentorRes(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhoneNumber(),
                    user.getAttachment().getContent(),
                    groupList
            );
            mentors.add(mentorRes);
        }

        return mentors;
    }


    @Override
    public MentorRes getMentorId(Long mentorId) {
        User user = userRepository.findById(mentorId).get();
        if (user.get_active()) {
            if (user.getRoles().contains(rolesRepository.findByName("ROLE_MENTOR"))){
                List<Group> groups = groupService.getGroupByMentorId(mentorId);
                return new MentorRes(user.getId(), user.getFirstName(),
                        user.getLastName(), user.getPhoneNumber(),
                        user.getAttachment().getContent(), groups);
            }
        }
        return null;
    }

    @Override
    public MentorRes updateMentor(Long mentorId, MentorUpdateReq mentorUpdateReq) {
        User user = userRepository.findById(mentorId).get();
        user.set_active(mentorUpdateReq.is_active());
        user.setPassword(passwordEncoder.encode(mentorUpdateReq.getPassword()));
        user.setRoles(mentorUpdateReq.getRoles());
        User save = userRepository.save(user);
        return new MentorRes(save.getId(),save.getFirstName(),
                save.getLastName(), save.getPhoneNumber(),
                save.getAttachment().getContent(),
                groupService.getGroupByMentorId(mentorId));
    }
}

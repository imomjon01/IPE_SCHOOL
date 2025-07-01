package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.component.GroupMapper;
import ipe.school.ipe_school.models.dtos.req.GroupReq;
import ipe.school.ipe_school.models.dtos.req.UpdatetedStudentReq;
import ipe.school.ipe_school.models.dtos.res.*;
import ipe.school.ipe_school.models.entity.*;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.repo.*;
import ipe.school.ipe_school.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final StudentProgressRepository studentProgressRepository;
    private final ScienceRepository scienceRepository;

    @Override
    public GroupRes createGroup(GroupReq groupReq) {
        Group group = Group.builder().
                name(groupReq.getName())
                .active(true)
                .build();
        Group savedGroup = groupRepository.save(group);
        return new GroupRes(savedGroup.getId(), savedGroup.getName());
    }

    @Override
    public List<GroupDetailsRes> getGroupsBy_Active() {
        List<Group> groups = groupRepository.findAllByActive(true);
        return groups.stream()
                .map(groupMapper::toDetailsDTO)
                .toList();
    }

    @Override
    public GroupDetailsRes getGroupById(Long groupId) {
        Group group = groupRepository.getGroupById(groupId);
        return groupMapper.toDetailsDTO(groupRepository.save(group));
    }

    @Transactional
    @Override
    public GroupDetailsRes updateGroup(Long groupId, GroupReq groupReq) {
        Group group = groupRepository.getGroupById(groupId);
        group.setName(groupReq.getName());
        return groupMapper.toDetailsDTO(groupRepository.save(group));
    }

    @Transactional
    @Override
    public void updateGroup_Active(Long groupId) {
        Group group = groupRepository.getGroupById(groupId);
        group.setActive(false);
        group.setMentor(null);
        List<Science> all = scienceRepository.findAll();
        for (Science science : all) {
            if (science.getGroups().contains(group)) {
                science.getGroups().remove(group);
                scienceRepository.save(science);
                break;
            }
        }
        groupRepository.save(group);
    }

    @Override
    public List<Group> getGroupByMentorId(Long mentorId) {
        return groupRepository.findByMentorId(mentorId);
    }

    @Override
    public List<Group> getAllGroup() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<Long> getGroupByStudentId(Long id) {
        return Optional.ofNullable(groupRepository.findGroupIdByStudentId(id));
    }

    @Override
    public List<GroupRes> getMentorGroups(User mentor) {
        User user = userRepository.findByPhoneNumber(mentor.getPhoneNumber());
        List<Group> groups = groupRepository.findByMentorId(user.getId());
        return groups.stream().map(item->new GroupRes(item.getId(), item.getName())).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateStudent(UpdatetedStudentReq updatetedStudentReq) {
        if (updatetedStudentReq.getOldGroupId() == null && updatetedStudentReq.getNewGroupId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found");
        }
        if (updatetedStudentReq.getOldGroupId() != null) {
            Optional<Group> byId = groupRepository.findById(updatetedStudentReq.getOldGroupId());
            if (byId.isPresent()) {
                Group group = byId.get();
                for (User student : group.getStudents()) {
                    if (student.getId().equals(updatetedStudentReq.getStudentId())) {
                        group.getStudents().remove(student);
                        groupRepository.save(group);
                        break;
                    }
                }

            }
        }
        if (updatetedStudentReq.getNewGroupId() != null) {
            Optional<Group> byId = groupRepository.findById(updatetedStudentReq.getNewGroupId());
            if (byId.isPresent()) {
                Group group = byId.get();
                Optional<User> byId1 = userRepository.findById(updatetedStudentReq.getStudentId());
                if (byId1.isPresent()) {
                    User user = byId1.get();
                    group.getStudents().add(byId1.get());
                    Optional<StudentProgress> byStudentId = studentProgressRepository.findByStudentId(user.getId());
                    if (byStudentId.isPresent()) {
                        StudentProgress studentProgress = byStudentId.get();
                        studentProgress.setGroupName(group.getName());
                        studentProgressRepository.save(studentProgress);
                    }
                }
                groupRepository.save(group);
            }
        }
    }

    @Transactional
    @Override
    public Page<GroupArxivedRes> getAllGroupsActiveFalse(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Group> groupPage;

        if (search != null && !search.trim().isEmpty()) {
            groupPage = groupRepository.findAllActiveGroupsWithSearch(search, false, pageable);
        } else {
            groupPage = groupRepository.findAllActiveGroup(false, pageable);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return groupPage.map(group -> new GroupArxivedRes(
                group.getId(),
                group.getName(),
                group.getUpdatedAt() != null ? group.getUpdatedAt().format(formatter) : null
        ));

    }

    @Override
    public void updateMentor(Long groupId, Long mentorId) {
        Optional<Group> byId = groupRepository.findById(groupId);
        if (byId.isPresent()) {
            Group group = byId.get();
            if (mentorId == null) {
                group.setMentor(null);
            }else {
                User user = userRepository.findById(mentorId).get();
                group.setMentor(user);
            }
            groupRepository.save(group);
        }else  {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found");
        }
    }

    @Transactional
    @Override
    public Page<GroupResToAdmin> getAllGroupsActiveTrue(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Group> groupPage;

        if (search != null && !search.trim().isEmpty()) {
            groupPage = groupRepository.findAllActiveGroupsWithSearch(search, true, pageable);
        } else {
            groupPage = groupRepository.findAllActiveGroup(true, pageable);
        }

        return  groupPage.map(group -> new GroupResToAdmin(
                group.getId(), group.getName(),
                group.getMentor() != null ? group.getMentor().getFullName() : "Mentor yo‘q",
                group.getStudents().stream().map(User::getFullName).toList(),
                group.getModules()
                        .stream().map(item ->
                                new ModuleRes(item.getId(), item.getModuleName(),
                                        item.getActive())).collect(Collectors.toList())
        ));

    }

    @Override
    public Module createModule(Long groupId, String moduleName) {
        Module module = Module.builder()
                .moduleName(moduleName)
                .active(true)
                .build();
        Module save = moduleRepository.save(module);
        Optional<Group> byId = groupRepository.findById(groupId);
        if (byId.isPresent()) {
            Group group = byId.get();
            group.getModules().add(save);
            groupRepository.save(group);
        }
        return save;
    }

    @Override
    public List<GroupRes> getGroupsForScience() {
        List<Group> groups = groupRepository.findAllByActive(true);
        List<Science> sciences = scienceRepository.findAll();

        List<Group> existingGroups = sciences.stream()
                .flatMap(science -> science.getGroups().stream())
                .toList();

        return groups.stream()
                .filter(group -> !existingGroups.contains(group))
                .map(item -> new GroupRes(item.getId(), item.getName()))
                .toList();
    }

    @Override
    public List<StudentProgressRes> getGroupProgress(Long groupId) {
        Optional<Group> byId = groupRepository.findById(groupId);
        if (byId.isPresent()) {
            Group group = byId.get();
            List<StudentProgressRes> list = new java.util.ArrayList<>(group.getStudentProgresses().stream()
                    .map(item -> new StudentProgressRes(
                            item.getStudent().getFullName(),
                            item.getTotalQuery(),
                            item.getPassedQuery(),
                            item.getTotalQuery() > 0 ? (item.getPassedQuery() * 100) / item.getTotalQuery() : 0
                    )).toList());
            list.sort((o1, o2) -> o2.getPassedQuery().compareTo(o1.getPassedQuery()));
            return list;
        }
        return null;
    }

    @Override
    public void updateModule(Long groupId, Long moduleId) {
        Optional<Group> byId = groupRepository.findById(groupId);
        if (byId.isPresent()) {
            Group group = byId.get();
            Optional<Module> byId1 = moduleRepository.findById(moduleId);
            if (byId1.isPresent()) {
                Module module = byId1.get();
                if (module.getActive()) {
                    group.getModules().removeAll(group.getModules());
                    group.getModules().add(module);
                    groupRepository.save(group);
                }
            }
        }
    }

}

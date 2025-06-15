package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.component.GroupMapper;
import ipe.school.ipe_school.models.dtos.req.GroupReq;
import ipe.school.ipe_school.models.dtos.req.UpdatetedStudentReq;
import ipe.school.ipe_school.models.dtos.res.GroupDetailsRes;
import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.entity.Group;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.GroupRepository;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;

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
    public GroupRes getGroupById(Long groupId) {
        Group group = groupRepository.getGroupById(groupId);
        return new GroupRes(group.getId(), group.getName());
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

    @Transactional
    public void nmadur(UpdatetedStudentReq updatetedStudentReq) {
        if (updatetedStudentReq.getOldGroupId() != null) {

        }
        Optional<Group> byId = groupRepository.findById(updatetedStudentReq.getOldGroupId());
        if (byId.isPresent()) {
            Group group = byId.get();
            for (User student : group.getStudents()) {
                if (student.getId().equals(updatetedStudentReq.getStudentId())) {
                    group.getStudents().remove(student);
                    break;
                }
            }
            groupRepository.save(group);
            Optional<User> byId1 = userRepository.findById(updatetedStudentReq.getStudentId());
            Optional<Group> byId2 = groupRepository.findById(updatetedStudentReq.getNewGroupId());
            if (byId1.isPresent() && byId2.isPresent()) {
                User user = byId1.get();
                byId2.get().getStudents().add(user);
                groupRepository.save(byId2.get());
                return;
            }
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found");
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
                group.getStudents().add(byId1.get());
                groupRepository.save(group);
            }
        }

    }
}

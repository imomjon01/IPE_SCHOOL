package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.component.GroupMapper;
import ipe.school.ipe_school.models.dtos.req.GroupReq;
import ipe.school.ipe_school.models.dtos.res.GroupDetailsRes;
import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.entity.Group;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.GroupRepository;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;

    @Override
    public GroupRes createGroup(GroupReq groupReq) {
        Group group= Group.builder().
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
        Group group=groupRepository.getGroupById(groupId);
        return new GroupRes(group.getId(), group.getName());
    }

    @Transactional
    @Override
    public GroupDetailsRes updateGroup(Long groupId, GroupReq groupReq) {
        Group group=groupRepository.getGroupById(groupId);
        group.setName(groupReq.getName());
        return groupMapper.toDetailsDTO(groupRepository.save(group));
    }

    @Transactional
    @Override
    public void updateGroup_Active(Long groupId) {
        Group group=groupRepository.getGroupById(groupId);
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

    @Override
    public List<GroupRes> getMentorGroups(User mentor) {
        User user = userRepository.findByPhoneNumber(mentor.getPhoneNumber());
        List<Group> groups = groupRepository.findByMentorId(user.getId());
        return groups.stream().map(item->new GroupRes(item.getId(), item.getName())).collect(Collectors.toList());
    }
}

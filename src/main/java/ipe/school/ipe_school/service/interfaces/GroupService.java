package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.GroupReq;
import ipe.school.ipe_school.models.dtos.req.UpdatetedStudentReq;
import ipe.school.ipe_school.models.dtos.res.GroupDetailsRes;
import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.models.entity.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    GroupRes createGroup(GroupReq groupReq);

    List<GroupDetailsRes> getGroupsBy_Active();

    GroupRes getGroupById(Long groupId);

    GroupDetailsRes updateGroup(Long groupId, GroupReq groupReq);

    void updateGroup_Active(Long groupId);

    List<Group> getGroupByMentorId(Long mentorId);

    List<Group> getAllGroup();

    Optional<Long> getGroupByStudentId(Long id);

    void updateStudent(UpdatetedStudentReq updatetedStudentReq);
}

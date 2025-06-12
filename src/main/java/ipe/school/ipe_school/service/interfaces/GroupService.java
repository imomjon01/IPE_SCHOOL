package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.GroupReq;
import ipe.school.ipe_school.models.dtos.res.GroupDetailsRes;
import ipe.school.ipe_school.models.dtos.res.GroupRes;

import java.util.List;

public interface GroupService {
    GroupRes createGroup(GroupReq groupReq);

    List<GroupDetailsRes> getGroupsBy_Active();

    GroupDetailsRes getGroupById(Long groupId);

    GroupDetailsRes updateGroup(Long groupId, GroupReq groupReq);
}

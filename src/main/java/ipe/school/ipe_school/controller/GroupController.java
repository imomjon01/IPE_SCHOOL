package ipe.school.ipe_school.controller;

import ipe.school.ipe_school.models.dtos.req.GroupReq;
import ipe.school.ipe_school.models.dtos.res.GroupDetailsRes;
import ipe.school.ipe_school.models.dtos.res.GroupRes;
import ipe.school.ipe_school.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + GROUP)
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping()
    public ResponseEntity<GroupRes> addGroup(@RequestBody GroupReq groupReq) {
        System.out.println(groupReq);
        GroupRes groupRes = groupService.createGroup(groupReq);
        return new ResponseEntity<>(groupRes, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<GroupDetailsRes>> getAllGroups() {
        List<GroupDetailsRes> groups = groupService.getGroupsBy_Active();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{group_id}")
    public ResponseEntity<GroupRes> getGroupById(@PathVariable Long group_id) {
        GroupRes groupDetailsRes = groupService.getGroupById(group_id);
        return new ResponseEntity<>(groupDetailsRes, HttpStatus.OK);
    }

    @PostMapping("/{group_id}")
    public ResponseEntity<GroupDetailsRes> updateGroup(@PathVariable Long group_id, @RequestBody GroupReq groupReq) {
        GroupDetailsRes groupDetailsRes = groupService.updateGroup(group_id, groupReq);
        return new ResponseEntity<>(groupDetailsRes, HttpStatus.OK);
    }

    @DeleteMapping("/{group_id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long group_id) {
        groupService.updateGroup_Active(group_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

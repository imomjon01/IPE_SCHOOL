package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.req.GroupReq;
import ipe.school.ipe_school.models.dtos.req.UpdatetedStudentReq;
import ipe.school.ipe_school.models.dtos.res.*;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/getAll")
    public ResponseEntity<List<GroupDetailsRes>> getAllGroups() {
        List<GroupDetailsRes> groups = groupService.getGroupsBy_Active();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDetailsRes> getGroupById(@PathVariable Long groupId) {
        GroupDetailsRes groupDetailsRes = groupService.getGroupById(groupId);
        return new ResponseEntity<>(groupDetailsRes, HttpStatus.OK);
    }

    @PostMapping("/{groupId}")
    public ResponseEntity<GroupDetailsRes> updateGroup(@PathVariable Long groupId, @RequestBody GroupReq groupReq) {
        GroupDetailsRes groupDetailsRes = groupService.updateGroup(groupId, groupReq);
        return new ResponseEntity<>(groupDetailsRes, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {
        groupService.updateGroup_Active(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateStudents")
    public ResponseEntity<?> updateGroupInStudents(@RequestBody UpdatetedStudentReq updatetedStudentReq) {
        groupService.updateStudent(updatetedStudentReq);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/archived")
    public ResponseEntity<Page<GroupArxivedRes>> getGroup(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(required = false) String search
    ) {
        Page<GroupArxivedRes> groupArxivedRes = groupService.getAllGroupsActiveFalse(page, size, search);
        return ResponseEntity.ok(groupArxivedRes);
    }

    @GetMapping("/getByPage")
    public ResponseEntity<Page<GroupResToAdmin>> getGroupByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(required = false) String search
    ) {
        Page<GroupResToAdmin> groupResToAdmins = groupService.getAllGroupsActiveTrue( page, size, search);
        return ResponseEntity.ok(groupResToAdmins);
    }

    @PostMapping("/createModule/{groupId}")
    public ResponseEntity<?> createGroup(@PathVariable Long groupId, @RequestParam String moduleName) {
        Module module = groupService.createModule(groupId, moduleName);
        System.out.println(module);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/getForScience")
    public ResponseEntity<List<GroupRes>> getGroup() {
        List<GroupRes> groups = groupService.getGroupsForScience();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }
}

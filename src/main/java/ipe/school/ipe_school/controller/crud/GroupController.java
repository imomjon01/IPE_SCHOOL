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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + GROUP)
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<GroupRes> addGroup(@RequestBody GroupReq groupReq) {
        GroupRes groupRes = groupService.createGroup(groupReq);
        return new ResponseEntity<>(groupRes, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {
        groupService.updateGroup_Active(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateStudents")
    public ResponseEntity<?> updateGroupInStudents(@RequestBody UpdatetedStudentReq updatetedStudentReq) {
        groupService.updateStudent(updatetedStudentReq);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/archived")
    public ResponseEntity<Page<GroupArxivedRes>> getGroup(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(required = false) String search
    ) {
        Page<GroupArxivedRes> groupArxivedRes = groupService.getAllGroupsActiveFalse(page, size, search);
        return ResponseEntity.ok(groupArxivedRes);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getByPage")
    public ResponseEntity<Page<GroupResToAdmin>> getGroupByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(required = false) String search
    ) {
        Page<GroupResToAdmin> groupResToAdmins = groupService.getAllGroupsActiveTrue( page, size, search);
        return ResponseEntity.ok(groupResToAdmins);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createModule/{groupId}")
    public ResponseEntity<?> createGroup(@PathVariable Long groupId, @RequestParam String moduleName) {
        Module module = groupService.createModule(groupId, moduleName);
        System.out.println(module);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getForScience")
    public ResponseEntity<List<GroupRes>> getGroup() {
        List<GroupRes> groups = groupService.getGroupsForScience();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getProgress/{groupId}")
    public ResponseEntity<List<StudentProgressRes>> getGroupProgress(@PathVariable Long groupId) {
        List<StudentProgressRes> groupProgressRes = groupService.getGroupProgress(groupId);
        return new ResponseEntity<>(groupProgressRes, HttpStatus.OK);
    }

    @PostMapping("/updateModule/{groupId}")
    public ResponseEntity<?> updateModule(@PathVariable Long groupId, @RequestParam Long moduleId) {
        groupService.updateModule(groupId, moduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

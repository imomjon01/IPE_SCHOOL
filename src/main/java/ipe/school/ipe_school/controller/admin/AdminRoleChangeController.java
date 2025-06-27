package ipe.school.ipe_school.controller.admin;

import ipe.school.ipe_school.models.dtos.req.UserIdReq;
import ipe.school.ipe_school.models.dtos.req.UserReq;
import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH+API_VERSION+ADMIN+ROLE)
@RequiredArgsConstructor
public class AdminRoleChangeController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserRes>> getUsersByRole(@RequestBody Long roleId ) {
        List<UserRes> userRes=userService.findByRole(roleId);
        return new ResponseEntity<>(userRes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserRes> changeUserRole(@RequestBody UserIdReq userIdReq) {
        System.out.println(userIdReq);
        UserRes userRes=userService.changeUserRole(userIdReq.getUserId(), userIdReq.getRoleIds());
        return new ResponseEntity<>(userRes, HttpStatus.OK);
    }

    @GetMapping("/getByPage")
    public ResponseEntity<Page<UserRes>> getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(required = false) String search
    ) {
        Page<UserRes> users = userService.findAllUsersActive(page, size, search, true);
        return ResponseEntity.ok(users);
    }

}

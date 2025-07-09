package ipe.school.ipe_school.controller.admin;

import ipe.school.ipe_school.models.dtos.req.UserIdReq;
import ipe.school.ipe_school.models.dtos.req.UserReq;
import ipe.school.ipe_school.models.dtos.res.StudentDetailsRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH+API_VERSION+ADMIN+ROLE)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminRoleChangeController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserRes>> getUsersByRole(@RequestBody Long roleId ) {
        List<UserRes> userRes=userService.findByRole(roleId);
        return new ResponseEntity<>(userRes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> changeUserRole(@RequestBody UserIdReq userIdReq, Authentication authentication) {
        User authenticatedUser = userService.getAuthenticatedUser(authentication);
        if (authenticatedUser.getId() != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only user with ID 1 can modify roles");
        }
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

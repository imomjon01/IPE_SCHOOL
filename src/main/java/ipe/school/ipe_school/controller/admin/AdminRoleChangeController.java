package ipe.school.ipe_school.controller.admin;

import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserRes> changeUserRole(@RequestBody Long userId, @RequestBody List<Long> roleIds ) {
        UserRes userRes=userService.changeUserRole(userId,roleIds);
        return new ResponseEntity<>(userRes, HttpStatus.OK);
    }
}

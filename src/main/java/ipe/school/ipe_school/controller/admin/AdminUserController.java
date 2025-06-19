package ipe.school.ipe_school.controller.admin;

import ipe.school.ipe_school.models.dtos.res.*;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + ADMIN + USER)
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @DeleteMapping("/{userId}")
    public ResponseEntity<GroupRes> delete_mentor(@PathVariable Long userId) {
        userService.updateUser_Active(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/restore/{userId}")
    public ResponseEntity<UserRes> restoreMentor(@PathVariable Long userId) {
        UserRes userRes = userService.restoration(userId);
        return new ResponseEntity<>(userRes, HttpStatus.OK);
    }

    @GetMapping("/archived")
    public ResponseEntity<Page<UserRes>> getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(required = false) String search
    ) {
        Page<UserRes> users = userService.findAllUsersActive(page, size, search, false);
        return ResponseEntity.ok(users);
    }

}

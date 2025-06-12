package ipe.school.ipe_school.controller;


import ipe.school.ipe_school.models.dtos.req.LoginDto;
import ipe.school.ipe_school.models.dtos.req.RegisterDto;
import ipe.school.ipe_school.models.dtos.res.LoginRes;
import ipe.school.ipe_school.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<LoginRes> login(@RequestBody LoginDto loginDto) {
        LoginRes loginRes = authService.login(loginDto);
        return ResponseEntity.ok(loginRes);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
        return ResponseEntity.ok().build();
    }
}

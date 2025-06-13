package ipe.school.ipe_school.controller;


import ipe.school.ipe_school.models.dtos.req.LoginDto;
import ipe.school.ipe_school.models.dtos.req.RegisterDto;
import ipe.school.ipe_school.models.dtos.res.LoginRes;
import ipe.school.ipe_school.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        LoginRes loginRes = authService.login(loginDto);

        // Determine redirect URL based on roles
        String redirectUrl = determineRedirectUrl(loginRes.getRoles());

        return ResponseEntity.ok(Map.of(
                "token", loginRes.getToken(),
                "firstName", loginRes.getFirstName(),
                "lastName", loginRes.getLastName(),
                "phoneNumber", loginRes.getPhoneNumber(),
                "roles", loginRes.getRoles(),
                "redirect", redirectUrl
        ));
    }

    private String determineRedirectUrl(List<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            return "http://localhost:63342/IPE_SCHOOL/static/adminCabinet.html";
        } else if (roles.contains("ROLE_MENTOR")) {
            return "http://localhost:63342/IPE_SCHOOL/static/mentorCabinet.html";
        } else {
            return "http://localhost:63342/IPE_SCHOOL/static/studentCabinet.html";
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
        return ResponseEntity.ok().build();
    }
}

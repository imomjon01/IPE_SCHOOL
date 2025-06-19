package ipe.school.ipe_school.controller.login;


import ipe.school.ipe_school.models.dtos.req.LoginDto;
import ipe.school.ipe_school.models.dtos.req.RegisterDto;
import ipe.school.ipe_school.models.dtos.req.UserReq;
import ipe.school.ipe_school.models.dtos.res.LoginRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            LoginRes loginRes = authService.login(loginDto);

            String base64Image = loginRes.getImage() != null
                    ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(loginRes.getImage())
                    : null;

            String redirectUrl = determineRedirectUrl(loginRes.getRoles());

            return ResponseEntity.ok(Map.of(
                    "token", loginRes.getToken(),
                    "firstName", loginRes.getFirstName(),
                    "lastName", loginRes.getLastName(),
                    "phoneNumber", loginRes.getPhoneNumber(),
                    "roles", loginRes.getRoles(),
                    "image", base64Image,
                    "redirect", redirectUrl,
                    "userId", loginRes.getUserId()
            ));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Telefon raqam yoki parol noto‘g‘ri"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Tizimda xatolik yuz berdi"
            ));
        }
    }

    private String determineRedirectUrl(List<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            return "/adminCabinet.html";
        } else if (roles.contains("ROLE_MENTOR")) {
            return "/mentorCabinet.html";
        } else {
            return "/studentCabinet.html";
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(
            @RequestPart("id") String id,
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("phoneNumber") String phoneNumber,
            @RequestPart(value = "password", required = false) String password, // Optional password
            @RequestPart(value = "file", required = false) MultipartFile file) {

        try {
            UserReq userReq = new UserReq(
                    Long.parseLong(id),
                    firstName,
                    lastName,
                    phoneNumber,
                    password
            );

            UserRes userRes = authService.updateUser(userReq, file);

            // Yangilangan foydalanuvchi ma'lumotlarini olish
            String base64Image = file != null ?
                    "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(file.getBytes()) :
                    null;

            return ResponseEntity.ok(Map.of(
                    "id", userRes.getId(),
                    "firstName", userRes.getFirstName(),
                    "lastName", userRes.getLastName(),
                    "phoneNumber", userRes.getPhoneNumber(),
                    "image", base64Image,
                    "roles", null
            ));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Noto'g'ri ID formati");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server xatosi: " + e.getMessage());
        }
    }

}

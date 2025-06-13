package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.LoginDto;
import ipe.school.ipe_school.models.dtos.req.RegisterDto;
import ipe.school.ipe_school.models.dtos.res.LoginRes;
import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.security.JwtService;
import ipe.school.ipe_school.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginRes login(LoginDto loginDto) {
        User user = userRepository.findByPhoneNumber(loginDto.getPhoneNumber());

        if (!user.get_active()) {
            throw new DisabledException("User account is not active. Please contact administrator.");
        }

        var auth = new UsernamePasswordAuthenticationToken(
                loginDto.getPhoneNumber(),
                loginDto.getPassword()
        );

        authenticationManager.authenticate(auth);
        String token = jwtService.generateToken(loginDto.getPhoneNumber());
        List<String> list = user.getRoles().stream().map(Roles::getName).toList();
        System.out.println(list + "===============");
        return new LoginRes(token, user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(), list,
                user.getAttachment().getContent());
    }

    @Override
    public void register(RegisterDto registerDto) {
//        String phoneNumber;
//        String password;
//        String confirmPassword;
//        String firstName;
//        String lastName;
        User user =User.builder()
                ._active(true)
                .phoneNumber(registerDto.getPhoneNumber())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .build();
        userRepository.save(user);
    }
}

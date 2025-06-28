package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.LoginDto;
import ipe.school.ipe_school.models.dtos.req.RegisterDto;
import ipe.school.ipe_school.models.dtos.req.UserReq;
import ipe.school.ipe_school.models.dtos.res.LoginRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.AttachmentRepository;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.security.JwtService;
import ipe.school.ipe_school.service.interfaces.AuthService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@MultipartConfig
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentRepository attachmentRepository;

    @Override
    public LoginRes login(LoginDto loginDto) {
        User user = userRepository.findByPhoneNumber(loginDto.getPhoneNumber());
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        } else if (!user.getActive()) {
            throw new UsernameNotFoundException("Username not found");
        }

        var auth = new UsernamePasswordAuthenticationToken(
                loginDto.getPhoneNumber(),
                loginDto.getPassword()
        );

        authenticationManager.authenticate(auth);
        String token = jwtService.generateToken(loginDto.getPhoneNumber());
        List<String> list = user.getRoles().stream().map(Roles::getName).toList();
        if (user.getAttachment() == null) {
            return new LoginRes(token, user.getId(), user.getFirstName(),
                    user.getLastName(), user.getPhoneNumber(), list,
                    null);
        }
        return new LoginRes(token, user.getId(), user.getFirstName(),
                user.getLastName(), user.getPhoneNumber(), list,
                user.getAttachment().getContent());
    }

    @Override
    public void register(RegisterDto registerDto) {
        User user = User.builder()
                .active(true)
                .phoneNumber(registerDto.getPhoneNumber())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .build();
        userRepository.save(user);
    }

    @SneakyThrows
    @Override
    public UserRes updateUser(UserReq userReq, MultipartFile file) {
        Optional<User> byId = userRepository.findById(userReq.getId());
        if (byId.isPresent()) {
            User user = byId.get();
            user.setFirstName(userReq.getFirstName());
            user.setLastName(userReq.getLastName());
            user.setPhoneNumber(userReq.getPhoneNumber());
            if (userReq.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userReq.getPassword()));
            }
            if (file != null) {
                user.getAttachment().setContent(file.getBytes());
                user.getAttachment().setContentType(file.getContentType());
                attachmentRepository.save(user.getAttachment());
            }
            User save = userRepository.save(user);
            return new UserRes(save.getId(), save.getFirstName(),
                    save.getLastName(), save.getPhoneNumber(),
                    save.getRoles().stream().map(Roles::getName).toList());
        } else {
            log.error("e: ", new ChangeSetPersister.NotFoundException());
            return null;
        }
    }
}

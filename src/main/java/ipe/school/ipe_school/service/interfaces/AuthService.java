package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.*;
import ipe.school.ipe_school.models.dtos.res.*;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    LoginRes login(LoginDto loginDto);

    void register(RegisterDto registerDto);

    UserRes updateUser(UserReq userReq,  MultipartFile file);
}

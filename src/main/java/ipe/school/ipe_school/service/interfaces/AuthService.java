package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.LoginDto;
import ipe.school.ipe_school.models.dtos.req.RegisterDto;
import ipe.school.ipe_school.models.dtos.req.UserReq;
import ipe.school.ipe_school.models.dtos.res.LoginRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    LoginRes login(LoginDto loginDto);

    void register(RegisterDto registerDto);

    UserRes updateUser(UserReq userReq,  MultipartFile file);
}

package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.LoginDto;
import ipe.school.ipe_school.models.dtos.req.RegisterDto;
import ipe.school.ipe_school.models.dtos.res.LoginRes;

public interface AuthService {
    LoginRes login(LoginDto loginDto);

    void register(RegisterDto registerDto);
}

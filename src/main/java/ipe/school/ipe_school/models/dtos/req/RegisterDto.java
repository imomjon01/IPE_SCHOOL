package ipe.school.ipe_school.models.dtos.req;

import lombok.Value;

@Value
public class RegisterDto {
    String email;
    String password;
    String confirmPassword;
    String firstName;
    String lastName;
}

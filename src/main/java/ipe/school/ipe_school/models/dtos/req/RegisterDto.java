package ipe.school.ipe_school.models.dtos.req;

import lombok.Value;

@Value
public class RegisterDto {
    String phoneNumber;
    String password;
    String confirmPassword;
    String firstName;
    String lastName;
}

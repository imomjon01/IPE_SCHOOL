package ipe.school.ipe_school.models.dtos.req;

import lombok.Value;

@Value
public class LoginDto {
    Long id;
    String phoneNumber;
    String password;
}

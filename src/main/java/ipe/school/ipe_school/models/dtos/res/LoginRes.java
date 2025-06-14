package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

import java.util.List;

@Value
public class LoginRes {
    String token;
    Long userId;
    String firstName;
    String lastName;
    String phoneNumber;
    List<String> roles;
    byte[] image;
}

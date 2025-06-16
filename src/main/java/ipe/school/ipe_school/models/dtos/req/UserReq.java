package ipe.school.ipe_school.models.dtos.req;

import lombok.Value;

@Value
public class UserReq {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    String password;
}

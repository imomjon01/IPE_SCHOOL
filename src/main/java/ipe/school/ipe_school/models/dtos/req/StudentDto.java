package ipe.school.ipe_school.models.dtos.req;

import lombok.Value;

@Value
public class StudentDto {
    String firstName;
    String lastName;
    String phoneNumber;
    String password;
}

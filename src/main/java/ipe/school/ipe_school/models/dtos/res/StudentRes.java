package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

@Value
public class StudentRes {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    String password;
    byte[] image;
}


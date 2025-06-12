package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

@Value
public class StudentDetailsRes {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
}


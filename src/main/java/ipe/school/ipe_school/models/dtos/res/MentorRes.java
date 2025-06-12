package ipe.school.ipe_school.models.dtos.res;

import ipe.school.ipe_school.models.entity.Group;
import lombok.Value;

import java.util.List;

@Value
public class MentorRes {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    byte[] image;
    List<Group> groups;
}

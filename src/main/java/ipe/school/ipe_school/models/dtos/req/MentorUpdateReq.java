package ipe.school.ipe_school.models.dtos.req;

import ipe.school.ipe_school.models.entity.Roles;
import lombok.Value;

import java.util.List;

@Value
public class MentorUpdateReq {
    List<Roles> roles;
    boolean _active;
    String password;
}

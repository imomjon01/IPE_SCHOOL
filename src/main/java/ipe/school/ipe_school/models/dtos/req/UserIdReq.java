package ipe.school.ipe_school.models.dtos.req;

import lombok.Value;

import java.util.List;

@Value
public class UserIdReq {
    Long userId;
    List<Long> roleIds;
}

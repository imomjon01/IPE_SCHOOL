package ipe.school.ipe_school.models.dtos.req;

import lombok.Value;

import java.util.List;

@Value
public class ScienceReq {
    String name;
    List<Long> groupIds;
}

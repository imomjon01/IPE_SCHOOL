package ipe.school.ipe_school.models.dtos.req;

import lombok.Value;

@Value
public class UpdatetedStudentReq {

    Long studentId;
    Long newGroupId;
    Long oldGroupId;
}

package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

@Value
public class StudentProcessRes {
     String studentName;
     String groupName;
     Integer passedTasks;
}

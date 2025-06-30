package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

@Value
public class StudentProgressRes {
    String studentName;
    Integer totalQuery;
    Integer passedQuery;
    Integer progress;
}

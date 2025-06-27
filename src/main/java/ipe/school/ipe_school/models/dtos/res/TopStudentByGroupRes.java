package ipe.school.ipe_school.models.dtos.res;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopStudentByGroupRes {
    private Long studentId;
    private String fullName;
    private String groupName;
    private Integer totalQuery;
    private Integer passedQuery;
    private Integer failedQuery;
}


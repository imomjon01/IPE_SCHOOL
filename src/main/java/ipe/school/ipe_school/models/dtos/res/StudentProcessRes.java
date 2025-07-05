package ipe.school.ipe_school.models.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProcessRes {
     String studentName;
     String groupName;
     Integer totalQuery;
     Integer passedQuery;
     Integer failedQuery;
}
package ipe.school.ipe_school.models.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRes {
    Long id;
    Long attachmentId;
    String questionTest;
    List<String> variant;
    String currentAnswer;
}

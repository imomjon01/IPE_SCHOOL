package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

import java.util.List;

@Value
public class QuestionRes {
    Long id;
    Long attachmentId;
    String questionTest;
    List<String> variant;
    String currentAnswer;
}

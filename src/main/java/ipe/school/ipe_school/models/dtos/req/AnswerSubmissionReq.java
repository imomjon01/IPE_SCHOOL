package ipe.school.ipe_school.models.dtos.req;

import ipe.school.ipe_school.models.entity.Question;
import ipe.school.ipe_school.models.entity.User;
import jakarta.persistence.ManyToOne;
import lombok.Value;

@Value
public class AnswerSubmissionReq {
    Long questionId;
    String selectedAnswer;
    Boolean answerCorrect;
}

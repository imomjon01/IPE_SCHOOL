package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.AnswerSubmissionReq;
import ipe.school.ipe_school.models.dtos.res.StudentProcessRes;
import ipe.school.ipe_school.models.entity.User;

import java.util.List;

public interface AnswerSubmissionService {
    StudentProcessRes submitAnswer(User user, List<AnswerSubmissionReq> answerSubmissionReqs);
}

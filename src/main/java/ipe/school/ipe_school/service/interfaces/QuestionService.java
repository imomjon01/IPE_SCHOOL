package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.QuestionReq;
import ipe.school.ipe_school.models.dtos.res.QuestionRes;

public interface QuestionService {
    QuestionRes save(QuestionReq questionReq);

    void delete(Long questionId);

    QuestionRes updateQuestionBYId(Long questionId, QuestionReq questionReq);

    QuestionRes getQuestionById(Long questionId);
}

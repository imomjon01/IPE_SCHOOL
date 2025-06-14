package ipe.school.ipe_school.controller;

import ipe.school.ipe_school.models.dtos.req.AnswerSubmissionReq;
import ipe.school.ipe_school.models.dtos.res.StudentProcessRes;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.service.interfaces.AnswerSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH+API_VERSION+ANSWER+SUBMISSION)
@RequiredArgsConstructor
public class AnswerSubmissionController {

    private final AnswerSubmissionService answerSubmissionService;


    @PostMapping
    public ResponseEntity<StudentProcessRes> submitAnswer(@AuthenticationPrincipal User user, @RequestBody List<AnswerSubmissionReq> answerSubmissionReqs) {
        StudentProcessRes answerSubmissionResList = answerSubmissionService.submitAnswer(user,answerSubmissionReqs);
        return ResponseEntity.ok(answerSubmissionResList);
    }
}

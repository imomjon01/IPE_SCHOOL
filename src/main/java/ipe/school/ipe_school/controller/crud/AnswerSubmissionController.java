package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.req.AnswerSubmissionReq;
import ipe.school.ipe_school.models.dtos.res.StudentProcessRes;
import ipe.school.ipe_school.models.entity.AnswerSubmission;
import ipe.school.ipe_school.models.entity.StudentProgress;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.AnswerSubmissionRepository;
import ipe.school.ipe_school.models.repo.GroupRepository;
import ipe.school.ipe_school.models.repo.StudentProgressRepository;
import ipe.school.ipe_school.service.interfaces.AnswerSubmissionService;
import ipe.school.ipe_school.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + ANSWER + SUBMISSION)
@RequiredArgsConstructor
public class AnswerSubmissionController {

    private final AnswerSubmissionService answerSubmissionService;
    private final AnswerSubmissionRepository answerSubmissionRepository;
    private final TaskService taskService;
    private final StudentProgressRepository studentProgressRepository;
    private final GroupRepository groupRepository;

    @PostMapping
    public ResponseEntity<StudentProcessRes> submitAnswer(@AuthenticationPrincipal User user, @RequestBody List<AnswerSubmissionReq> answerSubmissionReqs) {
        StudentProcessRes answerSubmissionResList = answerSubmissionService.submitAnswer(user, answerSubmissionReqs);
        return ResponseEntity.ok(answerSubmissionResList);
    }

    @GetMapping("/{taskId}/results")
    public ResponseEntity<StudentProcessRes> getTaskResults(@AuthenticationPrincipal User user, @PathVariable Long taskId) {

        StudentProcessRes studentProcessRes = answerSubmissionService.results(user, taskId);

        return ResponseEntity.ok(studentProcessRes);
    }
}

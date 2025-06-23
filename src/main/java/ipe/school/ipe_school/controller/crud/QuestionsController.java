package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.req.QuestionReq;
import ipe.school.ipe_school.models.dtos.res.QuestionRes;
import ipe.school.ipe_school.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + QUESTIONS)
@RequiredArgsConstructor
public class QuestionsController {
    private final QuestionService questionService;

    @PostMapping()
    public ResponseEntity<QuestionRes> createTask(@RequestBody QuestionReq questionReq) {
        QuestionRes questionRes = questionService.save(questionReq);
        return ResponseEntity.ok(questionRes);
    }

    @DeleteMapping("/questionId")
    public ResponseEntity<?> deleteTask(@RequestParam Long questionId) {
        questionService.delete(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{questionId}")
    public ResponseEntity<QuestionRes> updateTask(@PathVariable Long questionId,@RequestBody QuestionReq questionReq) {
        QuestionRes questionRes=questionService.updateQuestionBYId(questionId,questionReq);
        return ResponseEntity.ok(questionRes);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionRes> getTask(@PathVariable Long questionId) {
        QuestionRes questionRes=questionService.getQuestionById(questionId);
        return ResponseEntity.ok(questionRes);
    }
}

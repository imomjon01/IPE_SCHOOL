package ipe.school.ipe_school.controller;

import ipe.school.ipe_school.models.base.BaseEntity;
import ipe.school.ipe_school.models.dtos.req.TaskReq;
import ipe.school.ipe_school.models.dtos.res.QuestionRes;
import ipe.school.ipe_school.models.dtos.res.TaskDetailsRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + TASK)
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskRes> addTask(@RequestBody TaskReq task) {
        TaskRes taskRes = taskService.addTask(task);
        return new ResponseEntity<>(taskRes, HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDetailsRes> getTask(@PathVariable Long taskId) {
        Task task = taskService.findByActiveTask(taskId);

        List<QuestionRes> questionResList = task.getQuestions().stream().map(question ->
                new QuestionRes(
                        question.getId(),
                        question.getAttachment() != null ? question.getAttachment().getId() : null,
                        question.getQuestionTest(),
                        question.getVariant(),
                        question.getCurrentAnswer()
                )
        ).collect(Collectors.toList());

        List<Long> attachmentIds = task.getAttachment().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());

        TaskDetailsRes taskDetailsRes = new TaskDetailsRes(
                task.getId(),
                task.getTaskName(),
                questionResList,
                attachmentIds,
                task.getYoutubeURL()
        );
        return ResponseEntity.ok(taskDetailsRes);
    }


    @PostMapping("/{taskId}")
    public ResponseEntity<TaskRes> updateTask(@PathVariable Long taskId, @RequestBody TaskReq taskReq) {
        TaskRes taskRes = taskService.updateTaskById(taskId, taskReq);
        return new ResponseEntity<>(taskRes, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.updateTask_active(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

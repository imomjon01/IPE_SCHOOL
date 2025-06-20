package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.component.TaskMapper;
import ipe.school.ipe_school.models.dtos.req.TaskReq;
import ipe.school.ipe_school.models.dtos.res.QuestionRes;
import ipe.school.ipe_school.models.dtos.res.TaskDetailsRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + TASK)
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaskRes> addTask(@ModelAttribute @Validated TaskReq task) {
        TaskRes taskRes = taskService.addTask(task);
        return new ResponseEntity<>(taskRes, HttpStatus.CREATED);
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<TaskRes> updateTask(@PathVariable Long taskId,@ModelAttribute @Validated  TaskReq taskReq) {
        TaskRes taskRes = taskService.updateTaskById(taskId, taskReq);
        return new ResponseEntity<>(taskRes, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.updateTask_active(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDetailsRes> getTask(@PathVariable Long taskId) {
        Task task = taskService.findByActiveTask(taskId);

        List<QuestionRes> questionResList = taskMapper.mapQuestions(task);
        List<Long> attachmentIds = taskMapper.extractAttachmentIds(task);
        TaskDetailsRes taskDetailsRes = taskMapper.buildTaskDetails(task, questionResList, attachmentIds);

        return ResponseEntity.ok(taskDetailsRes);
    }
}
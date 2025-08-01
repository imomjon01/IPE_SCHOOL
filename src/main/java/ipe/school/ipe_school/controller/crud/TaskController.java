package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.component.TaskMapper;
import ipe.school.ipe_school.models.dtos.req.TaskReq;
import ipe.school.ipe_school.models.dtos.res.QuestionRes;
import ipe.school.ipe_school.models.dtos.res.TaskDetailsRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PreAuthorize("hasRole('SUPER_MENTOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaskRes> addTask(@ModelAttribute @Validated TaskReq task) {
        TaskRes taskRes = taskService.addTask(task);
        return new ResponseEntity<>(taskRes, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('SUPER_MENTOR')")
    @PostMapping("/{taskId}")
    public ResponseEntity<TaskRes> updateTask(@PathVariable Long taskId, @ModelAttribute @Validated TaskReq taskReq) {
        TaskRes taskRes = taskService.updateTaskById(taskId, taskReq);
        return new ResponseEntity<>(taskRes, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.updateTask_active(taskId, false);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('STUDENT','MENTOR')")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDetailsRes> getTask(@PathVariable Long taskId) {
        Task task = taskService.findByActiveTask(taskId);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        List<QuestionRes> questionResList = taskMapper.mapQuestions(task);
        List<Long> attachmentIds = taskMapper.extractAttachmentIds(task);
        TaskDetailsRes taskDetailsRes = taskMapper.buildTaskDetails(task, questionResList, attachmentIds);
        return ResponseEntity.ok(taskDetailsRes);
    }

    @PreAuthorize("hasRole('SUPER_MENTOR')")
    @GetMapping("/getActives/{taskId}")
    public ResponseEntity<TaskDetailsRes> getTaskActive(@PathVariable Long taskId) {
        TaskDetailsRes taskDetailsRes =  taskService.getTask(taskId);
        return ResponseEntity.ok(taskDetailsRes);
    }

    @PreAuthorize("hasRole('SUPER_MENTOR')")
    @PostMapping("updateActive/{taskId}")
    public ResponseEntity<TaskDetailsRes> updateTaskActive(@PathVariable Long taskId , @RequestParam Boolean active) {
        taskService.updateTask_active(taskId, active);
        TaskDetailsRes task = taskService.getTask(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
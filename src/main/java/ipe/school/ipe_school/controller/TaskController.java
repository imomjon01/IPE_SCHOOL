package ipe.school.ipe_school.controller;

import ipe.school.ipe_school.models.dtos.req.TaskReq;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH+API_VERSION+TASK)
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskRes> addTask(@RequestBody TaskReq task) {
        TaskRes taskRes=taskService.addTask(task);
        return new ResponseEntity<>(taskRes, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskRes>> getTasks(@RequestBody Long moduleId) {
        List<TaskRes> taskReses=taskService.getTasksByModuleId(moduleId);
        return new ResponseEntity<>(taskReses, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskRes> getTask(@PathVariable Long taskId) {

    }

    @PostMapping("/{taskId}")
    public ResponseEntity<TaskRes> updateTask(@PathVariable Long taskId, @RequestBody TaskReq taskReq) {
        TaskRes taskRes=taskService.updateTaskById(taskId,taskReq);
        return new ResponseEntity<>(taskRes, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.updateTask_active(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

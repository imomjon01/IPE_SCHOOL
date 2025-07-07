package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.TaskReq;
import ipe.school.ipe_school.models.dtos.res.TaskDetailsRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.models.entity.User;

import java.util.List;

public interface TaskService {
    TaskRes addTask(TaskReq task);

    void updateTask_active(Long taskReq, Boolean active);

    TaskRes updateTaskById(Long taskId, TaskReq taskReq);

    Task findByActiveTask(Long taskId);

    List<Task> getAllTask();

    Task findById(Long taskId);

    TaskDetailsRes getTask(Long taskId);
}

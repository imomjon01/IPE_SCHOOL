package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.TaskReq;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.Task;

import java.util.List;

public interface TaskService {
    TaskRes addTask(TaskReq task);

    void updateTask_active(Long taskReq);

    TaskRes updateTaskById(Long taskId, TaskReq taskReq);

    void findByActiveTask(Long questionId);
}

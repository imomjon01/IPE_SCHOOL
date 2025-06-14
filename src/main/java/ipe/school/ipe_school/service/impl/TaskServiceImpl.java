package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.TaskReq;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.models.repo.AttachmentRepository;
import ipe.school.ipe_school.models.repo.ModuleRepository;
import ipe.school.ipe_school.models.repo.TaskRepository;
import ipe.school.ipe_school.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final ModuleRepository moduleRepository;

    @Override
    @Transactional
    public TaskRes addTask(TaskReq taskReq) {
        Task task=new Task();
        if (taskReq.getAttachments() != null) {
            List<Attachment> attachments = taskReq.getAttachments().stream().map(item -> {
                try {
                    return new Attachment(item.getContentType(), item.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
            List<Attachment> savedAttachments = attachmentRepository.saveAll(attachments);
            task.setAttachment(savedAttachments);
        }
        task.setTaskName(taskReq.getTaskName());
        task.setYoutubeURL(taskReq.getYoutubeURL());
        task.setActive(true);
        Task savedTask = taskRepository.save(task);
        return new TaskRes(savedTask.getId(), savedTask.getTaskName());
    }


    @Override
    @Transactional
    public void updateTask_active(Long taskReq) {
        Task task = taskRepository.findById(taskReq).orElseThrow(RuntimeException::new);
        task.setActive(false);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public TaskRes updateTaskById(Long taskId, TaskReq taskReq) {
        Task task = taskRepository.findById(taskId).orElseThrow(RuntimeException::new);
        task.setTaskName(taskReq.getTaskName());
        if (taskReq.getYoutubeURL() != null) {
            task.setYoutubeURL(taskReq.getYoutubeURL());
        }
        if (taskReq.getAttachments() != null) {
            List<Attachment> attachments = taskReq.getAttachments().stream().map(item -> new Attachment(item.getContentType(), item.getContentType().getBytes())).toList();
            attachments.forEach(attachment -> attachment.setActive(true));
            List<Attachment> savedAttachment = attachmentRepository.saveAll(attachments);
            task.setAttachment(savedAttachment);
        }
        taskRepository.save(task);
        return new TaskRes(task.getId(), task.getTaskName());
    }
}

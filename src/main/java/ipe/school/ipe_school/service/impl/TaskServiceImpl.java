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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final ModuleRepository moduleRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    @Transactional
    public TaskRes addTask(TaskReq taskReq) {
        logger.debug("Adding task with moduleId: {}", taskReq.getModuleId());
        Task task = new Task();
        if (taskReq.getAttachments() != null) {
            List<Attachment> attachments = taskReq.getAttachments().stream().map(item -> {
                try {
                    return new Attachment(item.getContentType(), item.getBytes());
                } catch (IOException e) {
                    logger.error("Failed to process attachment: {}", item.getOriginalFilename(), e);
                    throw new RuntimeException("Failed to process attachment: " + item.getOriginalFilename(), e);
                }
            }).toList();
            List<Attachment> savedAttachments = attachmentRepository.saveAll(attachments);
            task.setAttachment(savedAttachments);
        }
        task.setTaskName(taskReq.getTaskName());
        task.setYoutubeURL(taskReq.getYoutubeURL());
        task.setActive(true);
        Task savedTask = taskRepository.save(task);
        addCurrentModule(savedTask, taskReq.getModuleId());
        logger.info("Task added successfully: {}", savedTask.getId());
        return new TaskRes(savedTask.getId(), savedTask.getTaskName());
    }

    private void addCurrentModule(Task savedTask, Long moduleId) {
        logger.debug("Associating task {} with moduleId: {}", savedTask.getId(), moduleId);
        if (moduleId == null) {
            logger.error("Module ID is null");
            throw new IllegalArgumentException("Module ID must not be null");
        }
        Module currentModule = moduleRepository.findById(moduleId)
                .orElseThrow(() -> {
                    logger.error("Module not found for ID: {}", moduleId);
                    return new IllegalArgumentException("Module not found for ID: " + moduleId);
                });
        if (currentModule.getTasks() == null) {
            currentModule.setTasks(new ArrayList<>(List.of(savedTask)));
        } else {
            currentModule.getTasks().add(savedTask);
        }
        moduleRepository.save(currentModule);
        logger.debug("Task {} associated with module {}", savedTask.getId(), moduleId);
    }

    @Override
    @Transactional
    public void updateTask_active(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setActive(false);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public TaskRes updateTaskById(Long taskId, TaskReq taskReq) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTaskName(taskReq.getTaskName());
        if (taskReq.getYoutubeURL() != null) {
            task.setYoutubeURL(taskReq.getYoutubeURL());
        }
        if (taskReq.getAttachments() != null) {
            List<Attachment> attachments = taskReq.getAttachments().stream().map(item -> {
                try {
                    return new Attachment(item.getContentType(), item.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to process attachment", e);
                }
            }).toList();
            attachments.forEach(attachment -> attachment.setActive(true));
            List<Attachment> savedAttachments = attachmentRepository.saveAll(attachments);
            task.setAttachment(savedAttachments);
        }
        taskRepository.save(task);
        return new TaskRes(task.getId(), task.getTaskName());
    }

    @Override
    public Task findByActiveTask(Long taskId) {
        return taskRepository.findByIdActive(taskId, true);
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }
}
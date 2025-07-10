package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.component.TaskMapper;
import ipe.school.ipe_school.models.dtos.req.TaskReq;
import ipe.school.ipe_school.models.dtos.res.QuestionRes;
import ipe.school.ipe_school.models.dtos.res.TaskDetailsRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.*;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.repo.*;
import ipe.school.ipe_school.service.interfaces.TaskService;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final ModuleRepository moduleRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final UserService userService;
    private final UserRepository userRepository;
    private final AnswerSubmissionRepository answerSubmissionRepository;
    private final TaskMapper taskMapper;

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
        Task savedTask = taskRepository.save(task);
        addCurrentModule(savedTask, taskReq.getModuleId());
        logger.info("Task added successfully: {}", savedTask.getId());
        return new TaskRes(savedTask.getId(), savedTask.getTaskName(),savedTask.getActive());
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
    public void updateTask_active(Long taskId, Boolean active) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setActive(active);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public TaskRes updateTaskById(Long taskId, TaskReq taskReq) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTaskName(taskReq.getTaskName());
        if (taskReq.getYoutubeURL() != null) {
            task.setYoutubeURL(taskReq.getYoutubeURL());
        }else {
            task.setYoutubeURL(null);
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
        if (taskReq.getActive()!=null) {
            task.setActive(taskReq.getActive());
        }
        taskRepository.save(task);
        return new TaskRes(task.getId(), task.getTaskName(),task.getActive());
    }

    @Override
    public Task findByActiveTask(Long taskId) {
        return taskRepository.findByIdActive(taskId, true);
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(RuntimeException::new);
    }

    @Override
    public TaskDetailsRes getTask(Long taskId) {
        Optional<Task> getById = taskRepository.findById(taskId);
        if (getById.isEmpty()) return null;
        Task task = getById.get();
        List<QuestionRes> questionResList = taskMapper.mapQuestions(task);
        List<Long> attachmentIds = taskMapper.extractAttachmentIds(task);
        return taskMapper.buildTaskDetails(task, questionResList, attachmentIds);
    }

}
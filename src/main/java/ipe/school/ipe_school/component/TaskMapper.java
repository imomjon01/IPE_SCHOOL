package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.dtos.res.QuestionRes;
import ipe.school.ipe_school.models.dtos.res.TaskDetailsRes;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.models.base.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public List<QuestionRes> mapQuestions(Task task) {
        return task.getQuestions().stream()
                .map(question -> new QuestionRes(
                        question.getId(),
                        question.getAttachment() != null ? question.getAttachment().getId() : null,
                        question.getQuestionTest(),
                        question.getVariant(),
                        question.getCurrentAnswer()
                ))
                .collect(Collectors.toList());
    }

    public List<Long> extractAttachmentIds(Task task) {
        return task.getAttachment().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    public TaskDetailsRes buildTaskDetails(Task task, List<QuestionRes> questions, List<Long> attachmentIds) {
        return new TaskDetailsRes(
                task.getId(),
                task.getTaskName(),
                questions,
                attachmentIds,
                task.getYoutubeURL()
        );
    }
}


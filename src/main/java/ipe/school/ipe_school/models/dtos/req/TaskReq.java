package ipe.school.ipe_school.models.dtos.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class TaskReq {
    private List<MultipartFile> attachments;
    @NotNull(message = "Task name must not be null")
    private String taskName;
    private String youtubeURL;
    @NotNull(message = "Module ID must not be null")
    private Long moduleId;
}
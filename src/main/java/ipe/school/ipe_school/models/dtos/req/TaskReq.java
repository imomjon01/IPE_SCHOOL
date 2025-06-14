package ipe.school.ipe_school.models.dtos.req;

import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.models.entity.Question;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Value
public class TaskReq {
    List<MultipartFile> attachments;
    String taskName;
    String youtubeURL;
}

package ipe.school.ipe_school.models.dtos.req;

import ipe.school.ipe_school.models.dtos.res.QuestionRes;
import ipe.school.ipe_school.models.entity.Attachment;
import jakarta.persistence.ElementCollection;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Value
public class QuestionReq {
    MultipartFile attachment;
    String questionTest;
    List<String> variant;
    String currentAnswer;
    Long taskId;

}

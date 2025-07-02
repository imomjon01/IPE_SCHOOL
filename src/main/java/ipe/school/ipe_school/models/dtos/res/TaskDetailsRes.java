package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

import java.util.List;

@Value
public class TaskDetailsRes {
    Long id;
    String name;
    List<QuestionRes> questionRes;
    List<Long> attachmentId;
    String youtubeURL;
    Boolean active;
}

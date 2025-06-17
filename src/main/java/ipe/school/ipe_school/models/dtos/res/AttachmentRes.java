package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

@Value
public class AttachmentRes {
    Long id;
    String contentType;
    byte[] content;
}

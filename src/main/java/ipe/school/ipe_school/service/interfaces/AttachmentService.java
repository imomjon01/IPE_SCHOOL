package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.res.AttachmentRes;

import java.util.List;

public interface AttachmentService {
    AttachmentRes getAttachmentById(Long attachmentId);

    List<AttachmentRes> getAttachmentsById(List<Long> attachmentIds);
}

package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.AttachmentReq;
import ipe.school.ipe_school.models.dtos.res.AttachmentRes;
import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.models.repo.AttachmentRepository;
import ipe.school.ipe_school.service.interfaces.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;

    @Override
    public AttachmentRes getAttachmentById(Long attachmentId) {
        Attachment attachment = attachmentRepository.findByIdAndActiveTrue(attachmentId);
        return new AttachmentRes(attachment.getId(),attachment.getContentType(), attachment.getContent());
    }

    @Override
    public List<AttachmentRes> getAttachmentsById(List<Long> attachmentIds) {
        List<Attachment> attachments = attachmentRepository.findAllByIdInAndActiveTrue(attachmentIds);
        return attachments.stream().map(attachment -> new AttachmentRes(attachment.getId(),
                attachment.getContentType(),
                attachment.getContent()))
                .collect(Collectors.toList());
    }

    @Override
    public Long createAttachment(AttachmentReq attachmentReq) {
        try {
            Attachment attachment = new Attachment();
            attachment.setActive(true);
            attachment.setContentType(attachmentReq.getAttachment().getContentType());
            attachment.setContent(attachmentReq.getAttachment().getBytes());
            Attachment save = attachmentRepository.save(attachment);
            return save.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

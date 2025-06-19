package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.req.AttachmentReq;
import ipe.school.ipe_school.models.dtos.res.AttachmentRes;
import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.service.interfaces.AttachmentService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH+API_VERSION+ATTACHMENTS)
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/{attachmentId}")
    public ResponseEntity<AttachmentRes> getAttachment(@PathVariable("attachmentId") Long attachmentId) {
        AttachmentRes attachmentRes=attachmentService.getAttachmentById(attachmentId);
        return ResponseEntity.ok(attachmentRes);
    }

    @GetMapping
    public ResponseEntity<List<AttachmentRes>> getAllAttachments(@RequestBody List<Long> attachmentIds) {
        List<AttachmentRes> attachmentResList=attachmentService.getAttachmentsById(attachmentIds);
        return ResponseEntity.ok(attachmentResList);
    }
    @PostMapping
    public ResponseEntity<Long> createAttachment(@ModelAttribute AttachmentReq attachmentReq) {
        Long id=attachmentService.createAttachment(attachmentReq);
        return ResponseEntity.ok(id);
    }
}

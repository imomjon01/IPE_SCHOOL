package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.models.repo.AttachmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AttachmentCleanupScheduler {

    private final AttachmentRepository attachmentRepository;

    public AttachmentCleanupScheduler(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }


    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Tashkent")
    @Transactional
    public void deleteUnusedAttachments() {
        List<Attachment> unusedAttachments = attachmentRepository.findUnusedAttachments();

        for (Attachment attachment : unusedAttachments) {
            attachmentRepository.delete(attachment);
        }

        System.out.println("Deleted " + unusedAttachments.size() + " orphan attachments.");
    }
}

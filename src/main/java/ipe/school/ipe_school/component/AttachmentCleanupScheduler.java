package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.entity.Attachment;
import ipe.school.ipe_school.models.repo.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AttachmentCleanupScheduler {

    private final AttachmentRepository attachmentRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Tashkent")
    @Transactional
    public void deleteUnusedAttachments() {
        List<Attachment> unusedAttachments = attachmentRepository.findUnusedAttachments();

        attachmentRepository.deleteAll(unusedAttachments);
    }
}

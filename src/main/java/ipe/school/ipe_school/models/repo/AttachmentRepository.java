package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findByIdAndActiveTrue(Long id);
    List<Attachment> findAllByIdInAndActiveTrue(List<Long> ids);

    @Query("SELECT a FROM Attachment a WHERE " +
            "a.id NOT IN (SELECT u.attachment.id FROM User u WHERE u.attachment IS NOT NULL) AND " +
            "a.id NOT IN (SELECT q.attachment.id FROM Question q WHERE q.attachment IS NOT NULL) AND " +
            "a.id NOT IN (SELECT tAtt.id FROM Task t JOIN t.attachment tAtt)")
    List<Attachment> findUnusedAttachments();
}
package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findByIdAndActiveTrue(Long id);
    List<Attachment> findAllByIdInAndActiveTrue(List<Long> ids);
}
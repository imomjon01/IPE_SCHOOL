package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.QuestionUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionUserRepository extends JpaRepository<QuestionUser, Long> {
}
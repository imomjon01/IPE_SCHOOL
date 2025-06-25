package ipe.school.ipe_school.models.repo;

import ipe.school.ipe_school.models.entity.Science;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScienceRepository extends JpaRepository<Science, Long> {

    Optional<Science> findScienceByIdAndActive(Long scienceId, boolean active);

    List<Science> findAllSciencesByActive(boolean active);
}

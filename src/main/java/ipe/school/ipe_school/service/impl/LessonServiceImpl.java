package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.repo.TaskRepository;
import ipe.school.ipe_school.service.interfaces.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final TaskRepository taskRepository;
}

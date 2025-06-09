package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.repo.QuestionRepository;
import ipe.school.ipe_school.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
}

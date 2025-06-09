package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.repo.QuestionUserRepository;
import ipe.school.ipe_school.service.interfaces.QuestionUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionUserServiceImpl implements QuestionUserService {

    private final QuestionUserRepository questionUserRepository;
}

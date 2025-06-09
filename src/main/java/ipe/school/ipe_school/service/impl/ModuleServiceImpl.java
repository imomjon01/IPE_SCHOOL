package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.repo.ModuleRepository;
import ipe.school.ipe_school.service.interfaces.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
}

package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.repo.RolesRepository;
import ipe.school.ipe_school.service.interfaces.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RolesRepository rolesRepository;
}

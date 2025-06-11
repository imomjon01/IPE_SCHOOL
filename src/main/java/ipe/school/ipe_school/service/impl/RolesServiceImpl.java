package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.repo.RolesRepository;
import ipe.school.ipe_school.service.interfaces.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {
    private final RolesRepository rolesRepository;


    @Override
    public List<Roles> findAll() {
        return rolesRepository.findAll();
    }

    @Override
    public void saveAll(ArrayList<Roles> roles) {
        rolesRepository.saveAll(roles);
    }
}

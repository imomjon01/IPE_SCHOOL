package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.entity.Roles;

import java.util.ArrayList;
import java.util.List;

public interface RolesService {
    List<Roles> findAll();

    void saveAll(ArrayList<Roles> roles);
}

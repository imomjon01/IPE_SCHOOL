package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.dtos.res.GroupDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ModuleRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.models.entity.Group;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.entity.User;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

    public GroupDetailsRes toDetailsDTO(Group group) {
        GroupDetailsRes dto = new GroupDetailsRes();
        dto.setId(group.getId());
        dto.setName(group.getName());

        // Map mentor
        dto.setMentor(toUserRes(group.getMentor()));

        // Map students
        dto.setStudents(group.getStudents().stream()
                .map(this::toUserRes)
                .toList());

        // Map modules
        dto.setModules(group.getModules().stream()
                .map(this::toModuleRes)
                .toList());

        return dto;
    }

    private UserRes toUserRes(User user) {
        UserRes res = new UserRes();
        res.setId(user.getId());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setPhoneNumber(user.getPhoneNumber());
        return res;
    }

    private ModuleRes toModuleRes(Module module) {
        ModuleRes res = new ModuleRes();
        res.setId(module.getId());
        res.setModuleName(module.getModuleName());
        return res;
    }
}


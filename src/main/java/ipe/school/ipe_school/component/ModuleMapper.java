package ipe.school.ipe_school.component;

import ipe.school.ipe_school.models.dtos.res.ModuleDetailsRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.dtos.res.UserRes;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.models.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ModuleMapper {

    public ModuleDetailsRes toDetailsDto(Module module) {
        ModuleDetailsRes moduleDetailsRes=new ModuleDetailsRes();
        moduleDetailsRes.setModuleName(module.getModuleName());
        moduleDetailsRes.setId(module.getId());
        moduleDetailsRes.setTasks(module.getTasks().stream()
                .map(this::toTaskRes)
                .toList());
        return moduleDetailsRes;
    }

    private TaskRes toTaskRes(Task task) {
        if (task==null) return null;
        return new TaskRes(task.getId(), task.getTaskName(),task.getActive());
    }
}

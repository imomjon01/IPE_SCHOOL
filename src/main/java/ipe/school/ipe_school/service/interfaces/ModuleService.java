package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.ModuleReq;
import ipe.school.ipe_school.models.dtos.res.ModuleDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ModuleRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;

import java.util.List;

public interface ModuleService {
    ModuleRes createModule(ModuleReq moduleReq);

    List<ModuleRes> getAllModulesBy_active();

    ModuleDetailsRes getModuleById(Long moduleId);

    ModuleDetailsRes updateModule(Long moduleId, ModuleReq moduleReq);

    void updateModule_Active(Long moduleId);

    List<TaskRes> getAllModuleById(Long moduleId);
}

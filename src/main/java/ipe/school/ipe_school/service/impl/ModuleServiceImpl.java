package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.ModuleReq;
import ipe.school.ipe_school.models.dtos.res.ModuleDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ModuleRes;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.repo.ModuleRepository;
import ipe.school.ipe_school.service.interfaces.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public ModuleRes createModule(ModuleReq moduleReq) {
        Module module = Module.builder()
                .moduleName(moduleReq.getName())
                ._active(true)
                .build();
        Module savedModule = moduleRepository.save(module);
        return new ModuleRes(savedModule.getId(), savedModule.getModuleName());
    }

    @Override
    public List<ModuleRes> getAllModulesBy_active() {
        List<Module> modules = moduleRepository.findAllBy_active(true);
        return modules.stream().map(module -> new ModuleRes(module.getId(), module.getModuleName())).toList();
    }

    @Override
    public ModuleDetailsRes getModuleById(Long moduleId) {
        Module module = moduleRepository.findById(moduleId).orElseThrow(RuntimeException::new);

    }
}

package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.component.ModuleMapper;
import ipe.school.ipe_school.models.dtos.req.ModuleReq;
import ipe.school.ipe_school.models.dtos.res.ModuleDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ModuleRes;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.repo.ModuleRepository;
import ipe.school.ipe_school.service.interfaces.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

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
        return moduleMapper.toDetailsDto(module);
    }

    @Transactional
    @Override
    public ModuleDetailsRes updateModule(Long moduleId, ModuleReq moduleReq) {
        Module module = moduleRepository.findById(moduleId).orElseThrow(RuntimeException::new);
        module.setModuleName(moduleReq.getName());
        Module savedModule = moduleRepository.save(module);
        return moduleMapper.toDetailsDto(savedModule);
    }

    @Override
    @Transactional
    public void updateModule_Active(Long moduleId) {
        Module module = moduleRepository.findById(moduleId).orElseThrow(RuntimeException::new);
        module.set_active(false);
    }
}

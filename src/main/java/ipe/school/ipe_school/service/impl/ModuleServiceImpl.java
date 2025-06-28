package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.component.ModuleMapper;
import ipe.school.ipe_school.models.dtos.req.ModuleReq;
import ipe.school.ipe_school.models.dtos.res.ModuleDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ModuleRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.entity.Task;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.GroupRepository;
import ipe.school.ipe_school.models.repo.ModuleRepository;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public ModuleRes createModule(ModuleReq moduleReq) {
        Module module = Module.builder()
                .moduleName(moduleReq.getName())
                .active(true)
                .build();
        Module savedModule = moduleRepository.save(module);
        return new ModuleRes(savedModule.getId(), savedModule.getModuleName(), savedModule.getActive());
    }

    @Override
    public List<ModuleRes> getAllModulesBy_active(User user) {
        return Optional.ofNullable(userRepository.findByPhoneNumber(user.getPhoneNumber()))
                .map(u -> groupRepository.findGroupIdByStudentId(u.getId()))
                .map(groupRepository::findById)
                .flatMap(g -> g.map(group -> group.getModules().stream()
                        .map(module -> new ModuleRes(
                                module.getId(),
                                module.getModuleName(),
                                module.getActive()))
                        .collect(Collectors.toList())))
                .orElse(Collections.emptyList());
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
        module.setActive(false);
    }

    @Override
    public List<TaskRes> getAllModuleById(Long moduleId) {
        Module modules = moduleRepository.findAllByModuleId(moduleId);
        List<Task> tasks = modules.getTasks();
        List<TaskRes> taskResList = new ArrayList<>();
        for (Task task : tasks) {
            taskResList.add(new TaskRes(task.getId(), task.getTaskName()));
        }
        return taskResList;
    }
}

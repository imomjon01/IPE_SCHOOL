package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.component.ModuleMapper;
import ipe.school.ipe_school.models.dtos.req.ModuleReq;
import ipe.school.ipe_school.models.dtos.res.ModuleDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ModuleRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.*;
import ipe.school.ipe_school.models.entity.Module;
import ipe.school.ipe_school.models.repo.*;
import ipe.school.ipe_school.service.interfaces.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final AnswerSubmissionRepository answerSubmissionRepository;
    private final ScienceRepository scienceRepository;

    @Override
    @Transactional
    public ModuleRes createModule(ModuleReq moduleReq) {
        Module module = Module.builder()
                .moduleName(moduleReq.getName())
                .active(true)
                .build();
        Module savedModule = moduleRepository.save(module);
        addingCurrentScience(savedModule, moduleReq.getScienceId());
        return new ModuleRes(savedModule.getId(), savedModule.getModuleName(), savedModule.getActive());
    }

    private void addingCurrentScience(Module savedModule, Long scienceId) {
        if (scienceId != null) {
            Science science = scienceRepository.findById(scienceId).orElseThrow(RuntimeException::new);
            if (science.getModules() != null) {
                science.getModules().add(savedModule);
            } else {
                science.setModules(Collections.singletonList(savedModule));
            }
        }
    }

    @Override
    public List<ModuleRes> getAllModulesBy_active(User user) {
        return Optional.ofNullable(userRepository.findByPhoneNumber(user.getPhoneNumber()))
                .map(u -> groupRepository.findGroupIdByStudentId(u.getId()))
                .flatMap(groupRepository::findById)
                .map(Group::getModules)
                .stream()
                .flatMap(List::stream)
                .filter(Module::getActive)
                .map(module -> new ModuleRes(
                        module.getId(),
                        module.getModuleName(),
                        true
                ))
                .collect(Collectors.toList());
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
    public void updateModule_Active(Long moduleId, Boolean active) {
        Module module = moduleRepository.findById(moduleId).orElseThrow(RuntimeException::new);
        module.setActive(active);
    }

    @Override
    public List<TaskRes> getAllModuleById(User user, Long moduleId) {
        Module module = moduleRepository.findAllByModuleId(moduleId);
        User u = userRepository.findByPhoneNumber(user.getPhoneNumber());

        if (module == null || u == null) return Collections.emptyList();

        List<TaskRes> taskResList = new ArrayList<>();
        for (Task task : module.getTasks()) {
            boolean hasUnanswered = task.getQuestions().stream()
                    .anyMatch(q -> answerSubmissionRepository
                            .findByStudentIdAndQuestionId(u.getId(), q.getId()).isEmpty());

            if (task.getActive() && hasUnanswered) {
                taskResList.add(new TaskRes(task.getId(), task.getTaskName(), true));
            }
        }
        return taskResList;
    }
}

package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.dtos.req.ScienceReq;
import ipe.school.ipe_school.models.dtos.res.*;
import ipe.school.ipe_school.models.entity.Group;
import ipe.school.ipe_school.models.entity.Science;
import ipe.school.ipe_school.models.repo.GroupRepository;
import ipe.school.ipe_school.models.repo.ScienceRepository;
import ipe.school.ipe_school.service.interfaces.ScienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScienceServiceImpl implements ScienceService {
    private final ScienceRepository scienceRepository;
    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public ScienceRes addScience(ScienceReq scienceReq) {
        Science science = new Science();
        science.setName(scienceReq.getName());
        science.setActive(true);
        List<Group> groups = bringingGroupsThatBelongsToScience(scienceReq.getGroupIds());
        science.setGroups(groups);
        Science savedScience = scienceRepository.save(science);
        return new ScienceRes(savedScience.getId(), savedScience.getName());
    }

    private List<Group> bringingGroupsThatBelongsToScience(List<Long> groupIds) {
        if (groupIds == null || groupIds.size() == 0) {
            return null;
        }
        return groupRepository.findAllByIdInAndActiveTrue(groupIds);
    }

    @Override
    @Transactional
    public ScienceDetailsRes getScienceByIdAndActive(Long scienceId) {
        Science science = scienceRepository.findScienceByIdAndActive(scienceId, true).orElseThrow(RuntimeException::new);
        List<GroupRes> groupRes = wrapGroupToGroupRes(science);
        return new ScienceDetailsRes(science.getId(), science.getName(), groupRes,
                science.getModules().stream().map(item ->
                        new ModuleDetalRes(item.getId(),item.getModuleName(), item.getTasks().size(), item.getActive())).toList());
    }

    private static List<GroupRes> wrapGroupToGroupRes(Science science) {
        List<GroupRes> groupRes = null;
        if (science.getGroups() != null) {
            groupRes = science.getGroups()
                    .stream()
                    .map(item -> new GroupRes(item.getId(), item.getName()))
                    .toList();
        }
        return groupRes;
    }

    @Override
    @Transactional
    public List<ScienceDetailsRes> getAllSciencesByActive() {
        List<Science> sciences = scienceRepository.findAllSciencesByActive(true);
        return sciences.stream()
                .map(science -> {
                    List<GroupRes> groupRes = wrapGroupToGroupRes(science);
                    return new ScienceDetailsRes(science.getId(), science.getName(), groupRes,
                            science.getModules().stream().map(item ->
                                    new ModuleDetalRes(item.getId(),item.getModuleName(),
                                            item.getTasks().size(), item.getActive())).toList());
                }).toList();
    }

    @Override
    @Transactional
    public ScienceDetailsRes updateScience(Long scienceId, ScienceReq scienceReq) {
        Science science = scienceRepository.findById(scienceId).orElseThrow(RuntimeException::new);
        science.setName(scienceReq.getName());
        List<Group> groups = bringingGroupsThatBelongsToScience(scienceReq.getGroupIds());
        science.setGroups(groups);
        Science updatedScience = scienceRepository.save(science);
        List<GroupRes> groupRes = wrapGroupToGroupRes(updatedScience);
        return new ScienceDetailsRes(science.getId(), science.getName(), groupRes,
                science.getModules().stream().map(item ->
                        new ModuleDetalRes(item.getId(),item.getModuleName(),
                                item.getTasks().size(), item.getActive())).toList());
    }

    @Override
    @Transactional
    public void deleteScience(Long scienceId) {
        Science science = scienceRepository.findById(scienceId).orElseThrow(RuntimeException::new);
        science.setActive(false);
    }

    @Override
    public Long getScienceCount() {
        return scienceRepository.getCount();
    }

    @Override
    public void deleteScienceFromGroup(Long scienceId, Long groupId) {
        Optional<Science> byId = scienceRepository.findById(scienceId);
        if (byId.isPresent()) {
            Science science = byId.get();
            science.getGroups().removeIf(item -> item.getId().equals(groupId));
            scienceRepository.save(science);
        }
    }
}

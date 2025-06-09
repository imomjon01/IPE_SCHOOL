package ipe.school.ipe_school.service.impl;

import ipe.school.ipe_school.models.repo.GroupRepository;
import ipe.school.ipe_school.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
}

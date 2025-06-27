package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.ScienceReq;
import ipe.school.ipe_school.models.dtos.res.ScienceDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ScienceRes;

import java.util.List;

public interface ScienceService {
    ScienceRes addScience(ScienceReq scienceReq);

    ScienceDetailsRes getScienceByIdAndActive(Long scienceId);

    List<ScienceDetailsRes> getAllSciencesByActive();

    ScienceDetailsRes updateScience(Long scienceId, ScienceReq scienceId1);

    void deleteScience(Long scienceId);
}

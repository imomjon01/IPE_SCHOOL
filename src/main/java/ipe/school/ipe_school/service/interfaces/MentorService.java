package ipe.school.ipe_school.service.interfaces;

import ipe.school.ipe_school.models.dtos.req.MentorReq;
import ipe.school.ipe_school.models.dtos.req.MentorUpdateReq;
import ipe.school.ipe_school.models.dtos.res.MentorRes;

import java.util.List;

public interface MentorService {

    void updateMentor_Active(Long mentor_id);

    MentorRes createMentor(MentorReq mentorReq);

    List<MentorRes> getMentorsBy_Active();

    MentorRes getMentorId(Long mentorId);

    MentorRes updateMentor(Long mentorId, MentorUpdateReq mentorUpdateReq);
}

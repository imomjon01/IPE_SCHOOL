package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

import java.util.List;

@Value
public class GroupResToAdmin {
    Long id;
    String name;
    String mentorName;
    List<String > StudentsName;
    List<ModuleRes> moduleRes;

}

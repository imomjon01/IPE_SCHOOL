package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

import java.util.List;

@Value
public class ModuleDetailsRes {
    Long id;
    String moduleName;
    List<TaskRes> tasks;
}

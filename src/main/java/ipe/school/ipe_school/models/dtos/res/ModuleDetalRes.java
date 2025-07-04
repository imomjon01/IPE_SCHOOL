package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

@Value
public class ModuleDetalRes {
    Long id;
    String name;
    Integer taskCount;
    Boolean active;
}

package ipe.school.ipe_school.models.dtos.res;

import lombok.Value;

import java.util.List;

@Value
public class ScienceDetailsRes {
    Long id;
    String name;
    List<GroupRes> groups;
    List<ModuleDetalRes> modules;
}

package ipe.school.ipe_school.models.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDetailsRes {
    Long id;
    String moduleName;
    List<TaskRes> tasks;
    Boolean active;
}

package ipe.school.ipe_school.models.dtos.res;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleRes {
    Long id;
    String moduleName;
    Boolean active;
}

package ipe.school.ipe_school.models.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailsRes {
    private Long id;
    private String name;
    private UserRes mentor;
    private List<UserRes> students;
    private List<ModuleRes> modules;
}

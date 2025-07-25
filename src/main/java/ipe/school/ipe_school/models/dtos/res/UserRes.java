package ipe.school.ipe_school.models.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRes {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    List<String> roles;
}

package ipe.school.ipe_school.models.entity;

import ipe.school.ipe_school.models.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    @ManyToMany
    private List<Roles> roles;
    @ManyToMany
    private List<QuestionUser> questionUsers;
    @ManyToOne
    private Group group;
}

package ipe.school.ipe_school.models.entity;

import ipe.school.ipe_school.models.base.BaseEntity;
import ipe.school.ipe_school.models.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Roles extends BaseEntity {
    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role_list", joinColumns = @JoinColumn(name = "roles_id"))
    @Column(name = "role")
    private List<Role> role;
}

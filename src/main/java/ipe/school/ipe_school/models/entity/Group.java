package ipe.school.ipe_school.models.entity;

import ipe.school.ipe_school.models.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "groups")
public class Group extends BaseEntity {
    private String name;
    @ManyToOne
    private User mentor;
    @ManyToMany
    private List<User> students = new ArrayList<>();
    @OneToMany
    private List<Module> modules;
    @OneToMany
    private List<StudentProgress> studentProgresses;
}

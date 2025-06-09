package ipe.school.ipe_school.models.entity;

import ipe.school.ipe_school.models.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Lesson extends BaseEntity {
    private String name;
    @ManyToOne
    private Module module;
    @OneToMany
    private List<Task> tasks;
}

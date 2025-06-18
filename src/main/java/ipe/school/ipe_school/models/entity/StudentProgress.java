package ipe.school.ipe_school.models.entity;

import ipe.school.ipe_school.models.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StudentProgress extends BaseEntity {

    @ManyToOne
    private User student;
    private String groupName;
    private Integer totalQuery;
    private Integer passedQuery;
    private Integer failedQuery;
}

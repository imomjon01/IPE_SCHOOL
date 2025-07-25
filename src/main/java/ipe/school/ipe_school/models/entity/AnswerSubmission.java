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
public class AnswerSubmission extends BaseEntity {

    @ManyToOne
    private User student;

    @ManyToOne
    private Question question;

    private String selectedAnswer;
    private Boolean correct;
}

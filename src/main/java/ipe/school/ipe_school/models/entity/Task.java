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
public class Task extends BaseEntity {

    private String taskName;

    private String youtubeURL;
    @ManyToMany
    private List<Attachment> attachment;
    @OneToMany
    private List<Question> questions;
}

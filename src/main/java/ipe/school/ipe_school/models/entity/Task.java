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
    @OneToMany
    private List<Question> questions;
    private String taskName;
    @ManyToMany
    private List<Attachment> attachment;
    private String youtubeURL;
}

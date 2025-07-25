package ipe.school.ipe_school.models.entity;

import ipe.school.ipe_school.models.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Attachment extends BaseEntity {

    private String contentType;
    private byte[] content;

    public Attachment(Long id, Boolean _active, String contentType, byte[] content) {
        super(id, _active);
        this.contentType = contentType;
        this.content = content;
    }
}

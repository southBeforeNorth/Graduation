package com.backend.feature.maintenance.notice.entity;

import com.backend.comment.entity.UUIDPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "notice")
public class Notice extends UUIDPersistence {
    @Column(name = "CONTENT", columnDefinition = "LONGBLOB")
    private String content;
}

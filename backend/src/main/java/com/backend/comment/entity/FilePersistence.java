package com.backend.comment.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class FilePersistence extends UUIDPersistence {

    @Column(name = "FILE_NAME", length = 100)
    private String fileName;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "FILE_CONTENT", columnDefinition = "MEDIUMBLOB")
    private byte[] fileContent;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    @Column(name = "CONTENT_TYPE", length = 100)
    private String contentType;

    @Column(name = "LENGTH", length = 11)
    private int length;
}

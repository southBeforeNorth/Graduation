package com.backend.feature.maintenance.sportGround.entity;

import com.backend.comment.entity.UUIDPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "area")
public class Area extends UUIDPersistence {

    @Column(name = "AREA_NAME", length = 128, nullable = false)
    private String areaName;

    @Column(name="SEQUENCE")
    private int sequence;

    @Column(name = "AM1")
    private String am1;

    @Column(name = "AM2")
    private String am2;

    @Column(name = "AM3")
    private String am3;

    @Column(name = "AM4")
    private String am4;

    @Column(name = "AM5")
    private String am5;

    @Column(name = "PM1")
    private String pm1;

    @Column(name = "PM2")
    private String pm2;
}

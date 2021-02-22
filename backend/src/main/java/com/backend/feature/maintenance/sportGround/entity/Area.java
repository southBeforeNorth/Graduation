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

    @Column(name = "PM3")
    private String pm3;

    @Column(name = "PM4")
    private String pm4;

    @Column(name = "PM5")
    private String pm5;

    @Column(name = "PM6")
    private String pm6;

    @Column(name = "PM7")
    private String pm7;

    @Column(name = "PM8")
    private String pm8;

    @Column(name = "PM9")
    private String pm9;

    @Column(name = "PM10")
    private String pm10;

    @Column(name = "PM11")
    private String pm11;

}

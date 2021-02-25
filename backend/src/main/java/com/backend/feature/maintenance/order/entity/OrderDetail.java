package com.backend.feature.maintenance.order.entity;

import com.backend.comment.entity.UUIDPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orderDetail")
public class OrderDetail extends UUIDPersistence {

    @Column(name = "AREA_NAME", length = 128)
    private String areaName;

    @Column(name = "TIME", length = 64)
    private String time;

}

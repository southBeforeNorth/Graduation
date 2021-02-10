package com.backend.feature.maintenance.merchant.entity;

import com.backend.comment.entity.UUIDPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "merchant", uniqueConstraints = {
        @UniqueConstraint(columnNames = "MERCHANT_NAME")
})
public class Merchant extends UUIDPersistence {

    @Column(name = "MERCHANT_NAME", length = 128, nullable = false)
    private String merchantName;

    @Column(name="PASSWORD", length = 16)
    private String password;

    @Column(name="PHONE", length = 20)
    private String phone;

    @Column(name = "CONTACT_PERSON", length =32)
    private String contactPerson;

    @Column(name = "AREA")
    private String area;

    @Column(name = "DETAILED_ADDRESS")
    private String detailedAddress;

    @Column(name = "STATUS")
    private boolean active = false;

    @Column(name = "TYPE")
    private String type = "merchant";
}

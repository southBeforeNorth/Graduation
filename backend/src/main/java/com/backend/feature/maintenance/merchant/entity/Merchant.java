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

    @Column(name = "CONTACT_PERSON", length =32)
    private String ContactPerson;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DETAILED_ADDRESS")
    private String DetailedAddress;

    @Column(name = "STATUS")
    private boolean active = false;
}

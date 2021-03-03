package com.backend.feature.maintenance.order.entity;

import com.backend.comment.entity.UUIDPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orderInfo")
public class OrderInfo extends UUIDPersistence {

    @Column(name = "USER_NAME", length = 64)
    private String userName;

    @Column(name = "USER_ID", length = 36)
    private String userId;

    @Column(name = "MERCHANT_NAME", length = 128)
    private String merchantName;

    @Column(name = "MERCHANT_ID", length = 36)
    private String merchantId;

    @Column(name = "SPORT_GROUND_NAME", length = 128)
    private String sportGroundName;

    @Column(name = "SPORT_GROUND_ID", length = 36)
    private String sportGroundId;

    @Column(name = "TOTAL_PRICE")
    private int totalPrice;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    @Column(name = "CONTACT_MERCHANT")
    private String contactMerchant;

    //验证数字
    @Column(name = "CERTIFICATE")
    private String certificate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "IS_COMMENT")
    private Boolean isComment;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "ORDER_INFO_ID", referencedColumnName = "ID")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_COMMENT_ID", referencedColumnName = "ID")
    private OrderComment orderComment;

}

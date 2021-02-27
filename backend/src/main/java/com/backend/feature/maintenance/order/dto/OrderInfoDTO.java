package com.backend.feature.maintenance.order.dto;

import com.backend.feature.maintenance.order.entity.OrderDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderInfoDTO {
    private String id;
    private String userName;
    private String userId;
    private String merchantName;
    private String merchantId;
    private String sportGroundName;
    private String sportGroundId;
    private int totalPrice;
    private String contactName;
    private String contactPhone;
    private String contactMerchant;
    //验证数字
    private String certificate;
    private String status;
    private Date orderDate;
    private List<OrderDetailDTO> orderDetails = new ArrayList<>();
    private String createdBy;
    private Date createdTime;
    private String lastModifiedBy;
    private Date lastModifiedTime;
}

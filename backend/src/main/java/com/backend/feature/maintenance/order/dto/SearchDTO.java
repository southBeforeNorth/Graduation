package com.backend.feature.maintenance.order.dto;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class SearchDTO {

    private int page;
    private int pageSize;
    private int start;
    private String merchantId;
    private String userId;
    private String status;
    private String sportGroundName;
    private String userName;
    private Long startDate;
    private Long endDate;
}

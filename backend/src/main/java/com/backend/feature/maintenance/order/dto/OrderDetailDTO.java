package com.backend.feature.maintenance.order.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class OrderDetailDTO {

    private String id;
    private String areaName;
    private String time;

}

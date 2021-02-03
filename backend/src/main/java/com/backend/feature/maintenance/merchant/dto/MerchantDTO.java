package com.backend.feature.maintenance.merchant.dto;

import lombok.Data;

@Data
public class MerchantDTO {
    private String merchantName;
    private String phone;
    private String password;
    private String contactPerson;
    private String detailedAddress;
    private boolean active;
}

package com.backend.feature.maintenance.merchant.dto;

import lombok.Data;

@Data
public class MerchantDTO {
    private String merchantName;
    private String password;
    private String ContactPerson;
    private String city;
    private String DetailedAddress;
    private boolean active;
}

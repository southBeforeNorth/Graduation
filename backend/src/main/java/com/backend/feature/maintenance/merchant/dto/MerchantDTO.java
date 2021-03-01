package com.backend.feature.maintenance.merchant.dto;

import com.backend.feature.maintenance.picture.dto.PictureDTO;
import com.backend.feature.maintenance.picture.entity.Picture;
import lombok.Data;

import java.util.Date;

@Data
public class MerchantDTO {
    private String id;
    private String merchantName;
    private String phone;
    private String password;
    private String contactPerson;
    private String area;
    private String detailedAddress;
    private boolean active;
    private String type;
    private String createdBy;
    private Date createdTime;
    private String lastModifiedBy;
    private Date lastModifiedTime;
    private PictureDTO header;
}

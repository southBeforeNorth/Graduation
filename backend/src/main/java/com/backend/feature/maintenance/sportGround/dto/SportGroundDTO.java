package com.backend.feature.maintenance.sportGround.dto;

import com.backend.feature.maintenance.merchant.entity.Merchant;
import com.backend.feature.maintenance.picture.dto.PictureDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SportGroundDTO {
    private String id;
    private String name;
    private String type;
    private String phone;
    private String city;
    private String detailedAddress;
    private String weeks;
    private String price;
    private String timeArea;
    private String description;
    private List<AreaDTO> areas = new ArrayList<>();
    private String createdBy;
    private Date createdTime;
    private String lastModifiedBy;
    private Date lastModifiedTime;
    private List<PictureDTO> pictures = new ArrayList<>();
}

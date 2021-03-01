package com.backend.feature.maintenance.user.dto;

import com.backend.feature.maintenance.picture.dto.PictureDTO;
import com.backend.feature.maintenance.picture.entity.Picture;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String id;
    private String name;
    private String password;
    private Date birthDay;
    private String type;
    private String sex;
    private String createdBy;
    private String phone;
    private Date createdTime;
    private String lastModifiedBy;
    private Date lastModifiedTime;
    private PictureDTO header;
}

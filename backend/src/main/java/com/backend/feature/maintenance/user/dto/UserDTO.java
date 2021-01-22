package com.backend.feature.maintenance.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String name;
    private String password;
    private Date birthDay;
    private String sex;
}

package com.backend.feature.maintenance.notice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeDTO {
    private String id;
    private String content;
    private String lastModifiedBy;
    private Date lastModifiedTime;
}

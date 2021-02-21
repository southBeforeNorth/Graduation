package com.backend.feature.maintenance.sportGround.dto;

import lombok.Data;

@Data
public class PictureDTO {

    private String id;

    private String fileName;

    private byte[] fileContent;

    private String description;

    private String contentType;

    private int length;
}

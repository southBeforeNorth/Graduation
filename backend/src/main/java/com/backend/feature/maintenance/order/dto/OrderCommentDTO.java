package com.backend.feature.maintenance.order.dto;

import com.backend.feature.maintenance.picture.dto.PictureDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderCommentDTO {

    private String id;
    private int rate;
    private String comment;
    private Date createdTime;
    private List<PictureDTO> pictures = new ArrayList<>();
}

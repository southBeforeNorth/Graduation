package com.backend.feature.maintenance.order.dto;

import com.backend.feature.maintenance.picture.dto.PictureDTO;
import com.backend.feature.maintenance.picture.entity.Picture;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderCommentDTO {

    private String id;
    private int rate;
    private String comment;
    private List<PictureDTO> pictures = new ArrayList<>();
}

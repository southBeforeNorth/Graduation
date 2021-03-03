package com.backend.feature.maintenance.order.assembler;

import com.backend.feature.maintenance.order.dto.OrderCommentDTO;
import com.backend.feature.maintenance.order.entity.OrderComment;
import com.backend.feature.maintenance.picture.assembler.PictureDTOAssembler;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class OrderCommentDTOAssembler {

    private OrderCommentDTOAssembler() {

    }

    public static OrderCommentDTO convertToDTO(OrderComment orderComment) {
        OrderCommentDTO orderCommentDTO = new OrderCommentDTO();
        BeanUtils.copyProperties(orderComment, orderCommentDTO);
        if (Objects.nonNull(orderComment.getPictures())) {
            orderCommentDTO.setPictures(PictureDTOAssembler.convertToDTOList(orderComment.getPictures()));
        }
        return orderCommentDTO;
    }

    public static OrderComment convertToEntity(OrderCommentDTO orderCommentDTO) {
        OrderComment orderComment = new OrderComment();
        BeanUtils.copyProperties(orderCommentDTO, orderComment);
        if (Objects.nonNull(orderCommentDTO.getPictures())) {
            orderComment.setPictures(PictureDTOAssembler.convertToEntityList(orderCommentDTO.getPictures()));
        }
        return orderComment;
    }
}

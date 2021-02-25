package com.backend.feature.maintenance.order.assembler;

import com.backend.feature.maintenance.order.dto.OrderInfoDTO;
import com.backend.feature.maintenance.order.entity.OrderInfo;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class OrderInfoDTOAssembler {
    private OrderInfoDTOAssembler() {

    }

    public static OrderInfo convertToEntity(OrderInfoDTO orderInfoDTO) {
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderInfoDTO, orderInfo);

        if (Objects.nonNull(orderInfoDTO.getOrderDetails())) {
            orderInfo.setOrderDetails(OrderDetailDTOAssembler.convertToEntityList(orderInfoDTO.getOrderDetails()));
        }
        return orderInfo;
    }

    public static OrderInfoDTO convertToDTO(OrderInfo orderInfo) {
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        BeanUtils.copyProperties(orderInfo, orderInfoDTO);

        if (Objects.nonNull(orderInfo.getOrderDetails())) {
            orderInfoDTO.setOrderDetails(OrderDetailDTOAssembler.convertToDTOList(orderInfo.getOrderDetails()));
        }

        return orderInfoDTO;
    }
}

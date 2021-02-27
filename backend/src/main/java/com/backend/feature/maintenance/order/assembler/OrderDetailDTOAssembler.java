package com.backend.feature.maintenance.order.assembler;

import com.backend.feature.maintenance.dictionary.dto.DictionaryOptionDTO;
import com.backend.feature.maintenance.order.dto.OrderDetailDTO;
import com.backend.feature.maintenance.order.entity.OrderDetail;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailDTOAssembler {

    private OrderDetailDTOAssembler() {

    }

    public static List<OrderDetail> convertToEntityList(List<OrderDetailDTO> orderDetailDTOS) {
        return orderDetailDTOS.stream()
                .map(OrderDetailDTOAssembler::convertToEntity)
                .collect(Collectors.toList());
    }

    public static List<OrderDetailDTO> convertToDTOList(List<OrderDetail> orderDetailList) {
        return orderDetailList.stream()
                .map(OrderDetailDTOAssembler::convertToDTO)
                .sorted(Comparator.comparing(OrderDetailDTO::getTime))
                .collect(Collectors.toList());
    }

    public static OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        BeanUtils.copyProperties(orderDetail, orderDetailDTO);
        return orderDetailDTO;
    }

    public static OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(orderDetailDTO, orderDetail);
        return orderDetail;
    }
}

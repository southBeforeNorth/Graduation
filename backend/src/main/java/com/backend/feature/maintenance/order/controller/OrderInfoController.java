package com.backend.feature.maintenance.order.controller;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;
import com.backend.feature.maintenance.order.dto.OrderInfoDTO;
import com.backend.feature.maintenance.order.dto.SearchDTO;
import com.backend.feature.maintenance.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @PostMapping("/create")
    public CommonDTO<OrderInfoDTO> createSportGround(@RequestBody OrderInfoDTO orderInfoDTO) {
        return CommonDTOAssembler.convertTODTO(orderInfoService.create(orderInfoDTO));
    }

    @GetMapping("/getByPage")
    public CommonDTO<PageableDTO<OrderInfoDTO>> getOrderListByPage(SearchDTO searchDTO) {
        return orderInfoService.getOrderByPage(searchDTO);

    }

    @PutMapping("/changeStatus/{id}")
    public CommonDTO<OrderInfoDTO> changeOrderStatus(@PathVariable String id, @RequestBody OrderInfoDTO orderInfoDTO) {
        return CommonDTOAssembler.convertTODTO(orderInfoService.changeOrderStatusById(id, orderInfoDTO));
    }

    @DeleteMapping("/delete/{id}")
    public CommonDTO<OrderInfoDTO> delete(@PathVariable String id) {
        return CommonDTOAssembler.convertTODTO(orderInfoService.deleteOrderById(id));
    }

    @GetMapping("/getBySportGroundId/{id}")
    public CommonDTO<List<OrderInfoDTO>> getOrderInfoListBySportGroundId(
            @PathVariable String id,
            @RequestParam Long date
    ) {
        LocalDateTime localDate = Instant.ofEpochMilli(date).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return CommonDTOAssembler.convertTODTO(orderInfoService.getOrderInfosBySportGroundId(id, localDate));
    }

}

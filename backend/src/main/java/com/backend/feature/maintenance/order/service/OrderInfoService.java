package com.backend.feature.maintenance.order.service;

import com.backend.comment.util.UserUtils;
import com.backend.feature.maintenance.order.assembler.OrderInfoDTOAssembler;
import com.backend.feature.maintenance.order.dto.OrderInfoDTO;
import com.backend.feature.maintenance.order.entity.OrderInfo;
import com.backend.feature.maintenance.order.repository.OrderInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderInfoService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public OrderInfoDTO create(OrderInfoDTO orderInfoDTO) {
        OrderInfo orderInfo = OrderInfoDTOAssembler.convertToEntity(orderInfoDTO);
        String userId = UserUtils.getUserId();
        orderInfo.setUserId(userId);
        return OrderInfoDTOAssembler.convertToDTO(orderInfoRepository.save(orderInfo));
    }

    public List<OrderInfoDTO> getOrderInfosBySportGroundId(String id, LocalDateTime date) {
        Specification<OrderInfo> specification = getOrderInfoSpecification(id, date);
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll(specification);
        return orderInfoList
                .stream()
                .map(OrderInfoDTOAssembler::convertToDTO)
                .collect(Collectors.toList());
    }

    private Specification<OrderInfo> getOrderInfoSpecification(String sportGroundId, LocalDateTime date) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (StringUtils.isNotEmpty(sportGroundId)) {
                predicateList.add(cb.equal(root.get("sportGroundId"), sportGroundId));
            }
            if (Objects.nonNull(date)) {
                int year = date.getYear();
                int month = date.getMonthValue();
                int day = date.getDayOfMonth();

                LocalDateTime dateStart = LocalDateTime.of(year, month, day, 0, 0, 0);
                Instant instantStart = dateStart.atZone(ZoneId.systemDefault()).toInstant();
                Date start = Date.from(instantStart);
                predicateList.add(cb.greaterThanOrEqualTo(root.get("orderDate"), start));
                LocalDateTime dateEnd = LocalDateTime.of(year, month, day, 23, 59, 59);
                Instant instantEnd = dateEnd.atZone(ZoneId.systemDefault()).toInstant();
                Date end = Date.from(instantEnd);
                predicateList.add(cb.lessThanOrEqualTo(root.get("orderDate"), end));
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            query.where(cb.and(predicateList.toArray(predicates)));
            return query.getRestriction();
        };
    }
}

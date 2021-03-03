package com.backend.feature.maintenance.order.service;

import com.backend.comment.assembler.PageableDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;
import com.backend.comment.util.PageableUtils;
import com.backend.comment.util.UserUtils;
import com.backend.feature.maintenance.order.assembler.OrderCommentDTOAssembler;
import com.backend.feature.maintenance.order.assembler.OrderInfoDTOAssembler;
import com.backend.feature.maintenance.order.dto.OrderCommentDTO;
import com.backend.feature.maintenance.order.dto.OrderInfoDTO;
import com.backend.feature.maintenance.order.dto.SearchDTO;
import com.backend.feature.maintenance.order.entity.OrderComment;
import com.backend.feature.maintenance.order.entity.OrderInfo;
import com.backend.feature.maintenance.order.exception.OrderInfoException;
import com.backend.feature.maintenance.order.repository.OrderCommentRepository;
import com.backend.feature.maintenance.order.repository.OrderInfoRepository;
import com.backend.feature.maintenance.user.entity.User;
import com.backend.feature.maintenance.user.exception.UserException;
import com.backend.feature.maintenance.user.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderInfoService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderCommentRepository orderCommentRepository;

    public OrderInfoDTO deleteComment(String orderId, String commentId) {
        OrderInfo orderInfo = orderInfoRepository.findById(orderId)
                .orElseThrow(() -> new OrderInfoException(OrderInfoException.ORDER_INFO_NO_EXIST));
        OrderComment orderComment = orderCommentRepository.findById(commentId)
                .orElseThrow(() -> new OrderInfoException("comment is not existed"));
        orderInfo.setOrderComment(null);
        orderCommentRepository.deleteById(orderComment.getId());
        return OrderInfoDTOAssembler.convertToDTO(orderInfoRepository.saveAndFlush(orderInfo));
    }

    public OrderInfoDTO createComment(String orderInfoId, OrderCommentDTO orderCommentDTO) {
        OrderInfo orderInfo = orderInfoRepository.findById(orderInfoId)
                .orElseThrow(() -> new OrderInfoException(OrderInfoException.ORDER_INFO_NO_EXIST));
        orderInfo.setOrderComment(OrderCommentDTOAssembler.convertToEntity(orderCommentDTO));
        orderInfo.setIsComment(true);
        return OrderInfoDTOAssembler.convertToDTO(orderInfoRepository.saveAndFlush(orderInfo));
    }

    public OrderInfoDTO create(OrderInfoDTO orderInfoDTO) {
        OrderInfo orderInfo = OrderInfoDTOAssembler.convertToEntity(orderInfoDTO);
        String userId = UserUtils.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserException.USER_NO_EXIST));
        orderInfo.setUserId(userId);
        orderInfo.setContactName(user.getName());
        orderInfo.setContactPhone(user.getPhone());
        return OrderInfoDTOAssembler.convertToDTO(orderInfoRepository.save(orderInfo));
    }

    public OrderInfoDTO changeOrderStatusById(String id, OrderInfoDTO orderInfoDTO) {
        OrderInfo orderInfo = orderInfoRepository.findById(id)
                .orElseThrow(() -> new OrderInfoException(OrderInfoException.ORDER_INFO_NO_EXIST));
        if (StringUtils.equals(orderInfoDTO.getStatus(), "review") && StringUtils.equals(orderInfo.getStatus(), "new")) {
            Random rand = new Random();//生成随机数
            StringBuilder certificate = new StringBuilder();
            for (int a = 0; a < 6; a++) {
                certificate.append(rand.nextInt(10));//生成6位数字
            }
            orderInfo.setCertificate(certificate.toString());
        }
        if (StringUtils.equals(orderInfoDTO.getStatus(), "finish") && StringUtils.equals(orderInfo.getStatus(), "review")) {
            if (!StringUtils.equals(orderInfo.getCertificate(), orderInfoDTO.getCertificate())) {
                return orderInfoDTO;
            }
            orderInfo.setStatus(orderInfoDTO.getStatus());
        }
        orderInfo.setStatus(orderInfoDTO.getStatus());
        return OrderInfoDTOAssembler.convertToDTO(orderInfoRepository.saveAndFlush(orderInfo));
    }

    public List<OrderInfoDTO> getOrderInfosBySportGroundId(String id, LocalDateTime date) {
        Specification<OrderInfo> specification
                = getOrderInfoSpecification(id, date, null, null, null,
                null, null, null, null);
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll(specification);
        return orderInfoList
                .stream()
                .map(OrderInfoDTOAssembler::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderInfoDTO deleteOrderById(String id) {
        OrderInfo orderInfo = orderInfoRepository.findById(id)
                .orElseThrow(() -> new OrderInfoException(OrderInfoException.ORDER_INFO_NO_EXIST));
        String type = UserUtils.getUserType();
        if (StringUtils.equals(type, "manage")) {
            orderInfoRepository.deleteById(id);
            return OrderInfoDTOAssembler.convertToDTO(orderInfo);
        }
        if (StringUtils.equals(type, "user")) {
            orderInfo.setUserId(null);
        }
        if (StringUtils.equals(type, "merchant")) {
            orderInfo.setMerchantId(null);
        }
        return OrderInfoDTOAssembler.convertToDTO(orderInfoRepository.saveAndFlush(orderInfo));
    }

    public CommonDTO<PageableDTO<OrderInfoDTO>> getOrderByPage(SearchDTO searchDTO) {
        if (StringUtils.equals(UserUtils.getUserType(), "user")) {
            searchDTO.setUserId(UserUtils.getUserId());
        }
        if (StringUtils.equals(UserUtils.getUserType(), "merchant")) {
            searchDTO.setMerchantId(UserUtils.getUserId());
        }
        PageRequest pageRequest = PageableUtils.getPageable(searchDTO.getPage(), searchDTO.getPageSize(), searchDTO.getStart());
        Date startTime = null;
        Date endTime = null;
        if (Objects.nonNull(searchDTO.getStartDate()) && Objects.nonNull(searchDTO.getEndDate())) {
            startTime = new Date(searchDTO.getStartDate());
            endTime = new Date(searchDTO.getEndDate());
        }
        Specification<OrderInfo> specification =
                getOrderInfoSpecification(null, null,
                        searchDTO.getMerchantId(), searchDTO.getUserId(),
                        searchDTO.getStatus(), searchDTO.getSportGroundName(),
                        startTime, endTime,
                        searchDTO.getUserName()

                );
        Page<OrderInfo> orderInfoPage = orderInfoRepository.findAll(specification, pageRequest);
        List<OrderInfoDTO> orderInfoDTOList = orderInfoPage.getContent()
                .stream()
                .map(OrderInfoDTOAssembler::convertToDTO)
                .sorted(Comparator.comparing(OrderInfoDTO::getOrderDate, Comparator.nullsFirst(Comparator.naturalOrder())).reversed())
                .collect(Collectors.toList());

        return PageableDTOAssembler.convertToDTO(orderInfoDTOList, (int) orderInfoPage.getTotalElements(), orderInfoPage.getTotalPages());

    }

    private Specification<OrderInfo> getOrderInfoSpecification(
            String sportGroundId,
            LocalDateTime date,
            String merchantId,
            String userId,
            String status,
            String sportGroundName,
            Date startDate,
            Date endDate,
            String userName
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (StringUtils.isNotEmpty(sportGroundId)) {
                predicateList.add(cb.equal(root.get("sportGroundId"), sportGroundId));
            }

            if (StringUtils.isNotEmpty(userName)) {
                predicateList.add(cb.like(root.get("contactName"), "%" + userName + "%"));
            }

            if (StringUtils.isNotEmpty(merchantId)) {
                predicateList.add(cb.equal(root.get("merchantId"), merchantId));
            }
            if (StringUtils.isNotEmpty(userId)) {
                predicateList.add(cb.equal(root.get("userId"), userId));
            }
            if (StringUtils.isNotEmpty(status)) {
                List<String> statusList = Arrays.stream(status.split(";"))
                        .collect(Collectors.toList());
                CriteriaBuilder.In<Object> in = cb.in(root.get("status"));
                for (String s : statusList) {
                    in.value(s);
                }
                predicateList.add(in);
            }
            if (StringUtils.isNotEmpty(sportGroundName)) {
                predicateList.add(cb.like(root.get("sportGroundName"), "%" + sportGroundName + "%"));
            }
            if (Objects.nonNull(startDate)) {
                predicateList.add(cb.greaterThanOrEqualTo(root.get("orderDate"), startDate));
            }
            if (Objects.nonNull(endDate)) {
                predicateList.add(cb.lessThanOrEqualTo(root.get("orderDate"), endDate));
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

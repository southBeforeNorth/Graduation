package com.backend.feature.maintenance.order.repository;

import com.backend.feature.maintenance.order.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, String>, JpaSpecificationExecutor<OrderInfo> {

    List<OrderInfo> findAllBySportGroundId(String id);
}

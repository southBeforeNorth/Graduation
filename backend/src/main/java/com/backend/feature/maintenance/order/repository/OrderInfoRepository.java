package com.backend.feature.maintenance.order.repository;

import com.backend.feature.maintenance.order.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, String>, JpaSpecificationExecutor<OrderInfo> {
}

package com.backend.feature.maintenance.order.repository;

import com.backend.feature.maintenance.order.entity.OrderComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCommentRepository extends JpaRepository<OrderComment, String> {
}

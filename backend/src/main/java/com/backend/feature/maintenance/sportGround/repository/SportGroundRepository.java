package com.backend.feature.maintenance.sportGround.repository;

import com.backend.feature.maintenance.merchant.entity.Merchant;
import com.backend.feature.maintenance.sportGround.entity.SportGround;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SportGroundRepository extends JpaRepository<SportGround, String>, JpaSpecificationExecutor<SportGround> {
}

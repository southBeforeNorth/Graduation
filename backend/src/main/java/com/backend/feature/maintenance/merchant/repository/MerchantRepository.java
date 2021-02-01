package com.backend.feature.maintenance.merchant.repository;

import com.backend.feature.maintenance.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {
    Optional<Merchant> getByMerchantName(String merchantName);
}

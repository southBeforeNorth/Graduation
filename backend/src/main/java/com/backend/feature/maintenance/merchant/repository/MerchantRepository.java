package com.backend.feature.maintenance.merchant.repository;

import com.backend.feature.maintenance.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String>, JpaSpecificationExecutor<Merchant> {
    Optional<Merchant> getByMerchantName(String merchantName);
    Optional<Merchant> getByMerchantNameAndPassword(String merchantName, String password);
}

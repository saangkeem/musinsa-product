package com.musinsa.shop.repository;

import com.musinsa.shop.entity.BrandProductsAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandProductAuditRepository extends JpaRepository<BrandProductsAudit, Long> {
}

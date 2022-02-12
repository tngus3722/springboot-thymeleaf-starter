package com.thymeleaf.starter.repository.product;

import com.thymeleaf.starter.entity.product.ProductOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrderEntity, Long> {
}

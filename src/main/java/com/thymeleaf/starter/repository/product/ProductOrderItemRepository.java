package com.thymeleaf.starter.repository.product;


import com.thymeleaf.starter.entity.product.ProductOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderItemRepository extends JpaRepository<ProductOrderItemEntity, Long> {
}


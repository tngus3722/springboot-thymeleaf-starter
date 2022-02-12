package com.thymeleaf.starter.mapper.product;

import com.thymeleaf.starter.dto.response.product.ProductOrderItemResponse;
import com.thymeleaf.starter.entity.product.ProductOrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductOrderItemMapper extends ProductMapper {
    ProductOrderItemMapper INSTANCE = Mappers.getMapper(ProductOrderItemMapper.class);

    @Mapping(source = "productEntity", target = "productResponse")
    ProductOrderItemResponse toProductOrderItem(ProductOrderItemEntity productOrderItemEntity);
}

package com.thymeleaf.starter.mapper.product;

import com.thymeleaf.starter.dto.response.product.ProductOrderResponse;
import com.thymeleaf.starter.entity.product.ProductOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductOrderMapper extends ProductOrderItemMapper {
    ProductOrderMapper INSTANCE = Mappers.getMapper(ProductOrderMapper.class);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "productOrderItemEntities", target = "productOrderItemResponses")
    ProductOrderResponse toProductOrderResponse(ProductOrderEntity productOrderEntity);
}


package com.thymeleaf.starter.mapper.product;

import com.thymeleaf.starter.dto.response.product.ProductResponse;
import com.thymeleaf.starter.entity.product.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "id", target = "productId")
    ProductResponse toProduct(ProductEntity product);
}

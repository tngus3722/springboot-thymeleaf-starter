package com.thymeleaf.starter.mapper.category;

import com.thymeleaf.starter.dto.CategoryResponse;
import com.thymeleaf.starter.entity.category.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    // get childCategories recursively
    @Mapping(source = "childCategoryEntities", target = "childCategoryResponses")
    CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);

    // without childCategories Mapper
    //CategoryResponse toCategoryResponseWithoutChild(CategoryEntity categoryEntity);
}

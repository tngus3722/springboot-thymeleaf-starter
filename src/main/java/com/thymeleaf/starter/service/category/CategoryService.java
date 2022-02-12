package com.thymeleaf.starter.service.category;

import com.thymeleaf.starter.dto.CategoryRequest;
import com.thymeleaf.starter.dto.CategoryResponse;
import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getCategory();

    CategoryResponse getCategoryById(Long categoryId);

    CategoryResponse postCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);

    void deleteCategory(Long categoryId);
}

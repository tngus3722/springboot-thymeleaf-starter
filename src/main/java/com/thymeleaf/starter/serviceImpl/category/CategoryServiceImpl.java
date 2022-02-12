package com.thymeleaf.starter.serviceImpl.category;

import com.thymeleaf.starter.dto.CategoryRequest;
import com.thymeleaf.starter.dto.CategoryResponse;
import com.thymeleaf.starter.entity.category.CategoryEntity;
import com.thymeleaf.starter.enums.ErrorMessage;
import com.thymeleaf.starter.exception.staticException.RequestInputException;
import com.thymeleaf.starter.mapper.category.CategoryMapper;
import com.thymeleaf.starter.repository.category.CategoryRepository;
import com.thymeleaf.starter.service.category.CategoryService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponse> getCategory() {
        return categoryRepository.findRootCategory().stream().map(CategoryMapper.INSTANCE::toCategoryResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        return CategoryMapper.INSTANCE.toCategoryResponse(this.getCategoryEntity(categoryId));
    }

    @Transactional
    @Override
    public CategoryResponse postCategory(CategoryRequest categoryRequest) {
        // set root if parentCategoryId is null
        CategoryEntity categoryEntity = new CategoryEntity(categoryRequest);

        if (categoryRequest.getParentCategoryId() != null)
            categoryEntity.setParentCategoryEntity(this.getCategoryEntity(categoryRequest.getParentCategoryId()));

        categoryRepository.save(categoryEntity);

        return CategoryMapper.INSTANCE.toCategoryResponse(categoryEntity);
    }

    @Transactional
    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = this.getCategoryEntity(categoryId); // 계층이동은 금지한다
        categoryEntity.setCategoryName(categoryRequest.getCategoryName());
        entityManager.flush();
        entityManager.clear();
        return CategoryMapper.INSTANCE.toCategoryResponse(this.getCategoryEntity(categoryId));
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        // category delete only allowed when it has not children
        if (!this.getCategoryEntity(categoryId).getChildCategoryEntities().isEmpty())
            throw new RequestInputException(ErrorMessage.CHILD_CATEGORY_EXIST_EXCEPTION, false);
        categoryRepository.delete(this.getCategoryEntity(categoryId)); // note! soft delete
    }
    
    private CategoryEntity getCategoryEntity(Long categoryId) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryId);
        if (optionalCategoryEntity.isEmpty())
            throw new RequestInputException(ErrorMessage.CATEGORY_NOT_EXIST_EXCEPTION, false);
        return optionalCategoryEntity.get();
    }
}

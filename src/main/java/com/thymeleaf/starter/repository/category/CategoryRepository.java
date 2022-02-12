package com.thymeleaf.starter.repository.category;

import com.thymeleaf.starter.entity.category.CategoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.parentCategoryEntity is null ORDER BY c.id asc")
    List<CategoryEntity> findRootCategory();
}

package com.thymeleaf.starter.entity.category;

import com.thymeleaf.starter.dto.CategoryRequest;
import com.thymeleaf.starter.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@Setter
@Getter
@SQLDelete(sql = "UPDATE category SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
    @Basic
    @Column(name = "category_name")
    private String categoryName;
    @JoinColumn(name = "parent_category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity parentCategoryEntity = null;
    @OrderBy("id asc")
    @OneToMany(mappedBy = "parentCategoryEntity")
    private List<CategoryEntity> childCategoryEntities = new ArrayList<>();

    public CategoryEntity(CategoryRequest categoryRequest) {
        this.update(categoryRequest);
    }

    public void update(CategoryRequest categoryRequest) {
        this.categoryName = categoryRequest.getCategoryName();
    }

}

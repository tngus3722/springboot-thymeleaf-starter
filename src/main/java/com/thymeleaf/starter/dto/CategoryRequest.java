package com.thymeleaf.starter.dto;

import com.thymeleaf.starter.annotation.ValidationGroup;
import com.thymeleaf.starter.dto.response.BaseResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryRequest extends BaseResponse {
    @NotNull(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}, message = "category name is required ")
    @Size(min = 1 , groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}, message = "category name length 1 or more")
    private String categoryName;
    @Min(value = 1, groups = {ValidationGroup.Create.class} , message = "must be 1 or more")
    @Null(groups = {ValidationGroup.Update.class} , message = "parent category id null only when update")
    private Long parentCategoryId;
}

package com.thymeleaf.starter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thymeleaf.starter.dto.response.BaseResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Setter
@Getter
public class CategoryResponse extends BaseResponse {
    private Long id;
    private String categoryName;
    private List<CategoryResponse> childCategoryResponses = new ArrayList<>();
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

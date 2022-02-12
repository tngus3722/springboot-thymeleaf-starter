package com.thymeleaf.starter.controller;

import com.thymeleaf.starter.annotation.ValidationGroup;
import com.thymeleaf.starter.dto.CategoryRequest;
import com.thymeleaf.starter.dto.CategoryResponse;
import com.thymeleaf.starter.service.category.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public ModelAndView getCategory() {
        ModelAndView modelAndView = new ModelAndView("/category/index");
        modelAndView.addObject("categories", categoryService.getCategory());
        return modelAndView;
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(value = "category_id") Long categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryResponse> postCategory(@RequestBody @Validated(ValidationGroup.Create.class) CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.postCategory(categoryRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/category/{category_id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "category_id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/category/{category_id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable(value = "category_id") Long categoryId,
            @RequestBody @Validated(ValidationGroup.Update.class) CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, categoryRequest), HttpStatus.OK);
    }
}

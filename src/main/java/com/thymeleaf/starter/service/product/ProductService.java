package com.thymeleaf.starter.service.product;

import com.thymeleaf.starter.dto.page.CursorCriteria;
import com.thymeleaf.starter.dto.request.ProductRequest;
import com.thymeleaf.starter.dto.response.BaseCursorResponse;
import com.thymeleaf.starter.dto.response.product.ProductResponse;
import com.thymeleaf.starter.entity.product.ProductEntity;

public interface ProductService {

    BaseCursorResponse<ProductResponse> getProducts(CursorCriteria cursorCriteria);

    ProductEntity getLockedProductEntityById(Long productId);

    ProductResponse putProduct(Long productId, ProductRequest productRequest);

    ProductEntity getProductEntityById(Long productId);

    ProductResponse getProduct(Long productId);
}
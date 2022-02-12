package com.thymeleaf.starter.service.product;


import com.thymeleaf.starter.dto.page.OffsetCriteria;
import com.thymeleaf.starter.dto.response.BaseOffsetResponse;
import com.thymeleaf.starter.dto.response.product.ProductOrderResponse;

public interface ProductOrderService {

    public BaseOffsetResponse<ProductOrderResponse> getOrders(OffsetCriteria offsetCriteria);

    public ProductOrderResponse postOrder(Long productId);

    public void deleteOrder(Long orderId);
}

package com.thymeleaf.starter.dto.response.product;

import com.thymeleaf.starter.dto.response.BaseResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ProductOrderResponse extends BaseResponse {
    private Long orderId;
    private List<ProductOrderItemResponse> productOrderItemResponses;
    private Integer totalPrice;
    private Integer deliveryPrice;
}
package com.thymeleaf.starter.dto.response.product;

import com.thymeleaf.starter.dto.response.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ProductOrderItemResponse extends BaseResponse {
    private ProductResponse productResponse;
    private Integer orderCount;
    private Integer orderPrice;
}

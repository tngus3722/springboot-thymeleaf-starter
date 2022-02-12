package com.thymeleaf.starter.controller;

import com.thymeleaf.starter.annotation.ValidationGroup;
import com.thymeleaf.starter.dto.page.CursorCriteria;
import com.thymeleaf.starter.dto.page.OffsetCriteria;
import com.thymeleaf.starter.dto.request.ProductRequest;
import com.thymeleaf.starter.dto.response.BaseCursorResponse;
import com.thymeleaf.starter.dto.response.BaseOffsetResponse;
import com.thymeleaf.starter.dto.response.product.ProductOrderResponse;
import com.thymeleaf.starter.dto.response.product.ProductResponse;
import com.thymeleaf.starter.service.product.ProductOrderService;
import com.thymeleaf.starter.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductOrderService productOrderService;

    @GetMapping("")
    public ModelAndView getProducts(@ModelAttribute CursorCriteria cursorCriteria) {
        ModelAndView modelAndView = new ModelAndView("store/index");
        modelAndView.addObject("cursor", productService.getProducts(cursorCriteria));
        return modelAndView;
    }

    @GetMapping("/{productId}")
    public ModelAndView getProduct(@PathVariable Long productId) {
        ModelAndView modelAndView = new ModelAndView("store/detail");
        modelAndView.addObject("product", productService.getProduct(productId));
        return modelAndView;
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> putProducts(@PathVariable("productId") Long productId,
            @RequestBody @Validated(ValidationGroup.Update.class) ProductRequest productRequest) {
        return new ResponseEntity(productService.putProduct(productId, productRequest), HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity<BaseOffsetResponse> getOrders(@ModelAttribute OffsetCriteria offsetCriteria) {
        return new ResponseEntity(productOrderService.getOrders(offsetCriteria), HttpStatus.OK);
    }

    @PostMapping("/{productId}/order")
    public ResponseEntity<ProductOrderResponse> postOrder(@PathVariable("productId") Long productId) {
        return new ResponseEntity(productOrderService.postOrder(productId), HttpStatus.OK);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long productId) {
        productOrderService.deleteOrder(productId);
        return new ResponseEntity(HttpStatus.OK);
    }
}

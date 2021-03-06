package com.thymeleaf.starter.serviceImpl.product;

import com.thymeleaf.starter.dto.page.OffsetCriteria;
import com.thymeleaf.starter.dto.response.BaseOffsetResponse;
import com.thymeleaf.starter.dto.response.product.ProductOrderResponse;
import com.thymeleaf.starter.entity.product.ProductEntity;
import com.thymeleaf.starter.entity.product.ProductOrderEntity;
import com.thymeleaf.starter.entity.product.ProductOrderItemEntity;
import com.thymeleaf.starter.enums.ErrorMessage;
import com.thymeleaf.starter.exception.staticException.RequestInputException;
import com.thymeleaf.starter.exception.staticException.SoldOutException;
import com.thymeleaf.starter.mapper.product.ProductOrderMapper;
import com.thymeleaf.starter.repository.product.ProductOrderItemRepository;
import com.thymeleaf.starter.repository.product.ProductOrderRepository;
import com.thymeleaf.starter.repository.product.ProductRepository;
import com.thymeleaf.starter.repository.product.support.ProductOrderRepositorySupport;
import com.thymeleaf.starter.service.product.ProductOrderService;
import com.thymeleaf.starter.service.product.ProductService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductService productService;
    private final ProductOrderRepositorySupport productOrderRepositorySupport;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    @Value("${server.base.point}")
    private String basePoint;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public BaseOffsetResponse<ProductOrderResponse> getOrders(OffsetCriteria offsetCriteria) {
        List<ProductOrderResponse> productOrderResponses = productOrderRepositorySupport
                .findByOffsetCriteria(offsetCriteria)
                .stream().map(ProductOrderMapper.INSTANCE::toProductOrderResponse)
                .collect(Collectors.toList());

        return new BaseOffsetResponse<ProductOrderResponse>(productOrderResponses, offsetCriteria, basePoint + "product/order");
    }

    // ?????? ??????
    @Transactional
    @Override
    public ProductOrderResponse postOrder(Long productId) {
        ProductEntity productEntity = productService.getLockedProductEntityById(productId);
        productEntity.diffRemains(1);
        if (productEntity.isSoldOut())
            throw new SoldOutException(ErrorMessage.REMAINS_EXCEPTION, false);

        ProductOrderEntity productOrderEntity = new ProductOrderEntity(productEntity.getProductPrice());
        ProductOrderItemEntity productOrderItem = new ProductOrderItemEntity(productEntity, productOrderEntity);

        productRepository.save(productEntity);
        productOrderRepository.save(productOrderEntity);
        productOrderItemRepository.save(productOrderItem);

        entityManager.flush();
        entityManager.clear();

        return ProductOrderMapper.INSTANCE.toProductOrderResponse(this.getProductOrderEntity(productOrderEntity.getId()));
    }

    @Transactional
    @Override
    public void deleteOrder(Long orderId) {
        ProductOrderEntity productOrderEntity = this.getProductOrderEntity(orderId);

        for (ProductOrderItemEntity productOrderItemEntity : productOrderEntity.getProductOrderItemEntities()) {
            ProductEntity product = productService.getLockedProductEntityById(productOrderItemEntity.getProductEntity().getId());
            product.plusRemains(productOrderItemEntity.getOrderCount());
        }
        // ?????? ?????? ?????????????????? ????????? ?????????????????? ??????????????? ?????????
        productOrderRepository.delete(productOrderEntity); // soft cascade delete
    }

    private ProductOrderEntity getProductOrderEntity(Long orderId) {
        Optional<ProductOrderEntity> optionalProductOrderEntity = productOrderRepository.findById(orderId);
        if (optionalProductOrderEntity.isEmpty())
            throw new RequestInputException(ErrorMessage.PRODUCT_ORDER_NOT_EXIST_EXCEPTION, false);
        return optionalProductOrderEntity.get();
    }
}

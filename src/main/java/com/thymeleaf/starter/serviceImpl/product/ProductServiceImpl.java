package com.thymeleaf.starter.serviceImpl.product;

import com.thymeleaf.starter.dto.page.CursorCriteria;
import com.thymeleaf.starter.dto.request.ProductRequest;
import com.thymeleaf.starter.dto.response.BaseCursorResponse;
import com.thymeleaf.starter.dto.response.product.ProductResponse;
import com.thymeleaf.starter.entity.product.ProductEntity;
import com.thymeleaf.starter.enums.ErrorMessage;
import com.thymeleaf.starter.exception.staticException.RequestInputException;
import com.thymeleaf.starter.mapper.product.ProductMapper;
import com.thymeleaf.starter.repository.product.ProductRepository;
import com.thymeleaf.starter.repository.product.support.ProductRepositorySupport;
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

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductRepositorySupport productRepositorySupport;
    @Value("${server.base.point}")
    private String basePoint;
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional(readOnly = true)
    @Override
    public BaseCursorResponse<ProductResponse> getProducts(CursorCriteria cursorCriteria) {
        List<ProductResponse> productResponses = productRepositorySupport.findByCursorCriteria(cursorCriteria)
                .stream().map(ProductMapper.INSTANCE::toProduct).collect(Collectors.toList());

        return new BaseCursorResponse<ProductResponse>(productResponses, cursorCriteria, basePoint + "product");
    }

    @Transactional
    @Override
    public ProductResponse putProduct(Long productId, ProductRequest productRequest) {
        ProductEntity productEntity = this.getProductEntityById(productId);
        productEntity.update(productRequest);
        entityManager.flush();
        entityManager.clear();
        return ProductMapper.INSTANCE.toProduct(this.getProductEntityById(productId));
    }

    @Transactional(readOnly = true)
    @Override
    public ProductEntity getProductEntityById(Long productId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        if (optionalProductEntity.isEmpty())
            throw new RequestInputException(ErrorMessage.PRODUCT_NOT_EXIST_EXCEPTION, false);
        return optionalProductEntity.get();
    }

    @Transactional
    @Override
    public ProductEntity getLockedProductEntityById(Long productId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findByIdLock(productId);
        if (optionalProductEntity.isEmpty())
            throw new RequestInputException(ErrorMessage.PRODUCT_NOT_EXIST_EXCEPTION, false);
        return optionalProductEntity.get();
    }
}


package com.thymeleaf.starter.repository.product;

import com.thymeleaf.starter.entity.product.ProductEntity;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="10000")})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT p FROM ProductEntity p where p.id = ?1")
    Optional<ProductEntity> findByIdLock(Long id);
}

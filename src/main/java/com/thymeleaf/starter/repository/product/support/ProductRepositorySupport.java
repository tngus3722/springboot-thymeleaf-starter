package com.thymeleaf.starter.repository.product.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thymeleaf.starter.dto.page.BaseCriteria;
import com.thymeleaf.starter.dto.page.CursorCriteria;
import com.thymeleaf.starter.dto.page.OffsetCriteria;
import com.thymeleaf.starter.entity.product.ProductEntity;
import com.thymeleaf.starter.entity.product.QProductEntity;
import com.thymeleaf.starter.enums.SortBy;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositorySupport extends QuerydslRepositorySupport {

    private JPAQueryFactory jpaQueryFactory;

    public ProductRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(ProductEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ProductEntity> findByOffsetCriteria(OffsetCriteria offsetCriteria) {
        QProductEntity qProductEntity = QProductEntity.productEntity;

        OrderSpecifier orderSpecifier = this.getOrderSpecifier(qProductEntity, offsetCriteria);

        return jpaQueryFactory.selectFrom(qProductEntity)
                .orderBy(orderSpecifier)
                .orderBy(qProductEntity.id.desc())
                .limit(offsetCriteria.getLimit() + 1)
                .offset(offsetCriteria.getOffset())
                .fetch();
    }

    public List<ProductEntity> findByCursorCriteria(CursorCriteria cursorCriteria) {
        QProductEntity qProductEntity = QProductEntity.productEntity;

        OrderSpecifier orderSpecifier = this.getOrderSpecifier(qProductEntity, cursorCriteria);

        return jpaQueryFactory.selectFrom(qProductEntity)
                .where(this.getBooleanBuilderByCursor(qProductEntity, cursorCriteria))
                .orderBy(orderSpecifier)
                .orderBy(qProductEntity.id.desc())
                .limit(cursorCriteria.getLimit() + 1)
                .fetch();
    }

    private BooleanBuilder getBooleanBuilderByCursor(QProductEntity qProductEntity, CursorCriteria cursorCriteria) {

        String cursor = cursorCriteria.getCursor();
        if (StringUtils.equals("init", cursor))
            return null; // null ??? where????????? ???????????? ????????????.

        String[] cursorParsed = new String(Base64.decodeBase64(cursorCriteria.getCursor())).split(" ");
        String sort = cursorCriteria.getSortBy();
        String order = cursorCriteria.getOrder();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.equals(sort, SortBy.id.toString()) && StringUtils.equals(order, "asc"))
            return booleanBuilder.and(qProductEntity.id.gt(Integer.valueOf(cursorParsed[0])));

        else if (StringUtils.equals(sort, SortBy.productPrice.toString()) && StringUtils.equals(order, "desc"))
            return booleanBuilder.or(qProductEntity.id.lt(Long.valueOf(cursorParsed[1])))
                    .and(qProductEntity.productPrice.eq(Integer.valueOf(cursorParsed[0])))
                    .or(qProductEntity.productPrice.lt(Integer.valueOf(cursorParsed[0])));

        else if (StringUtils.equals(sort, SortBy.productPrice.toString()) && StringUtils.equals(order, "asc"))
            return booleanBuilder.or(qProductEntity.id.lt(Long.valueOf(cursorParsed[1])))
                    .and(qProductEntity.productPrice.eq(Integer.valueOf(cursorParsed[0])))
                    .or(qProductEntity.productPrice.gt(Integer.valueOf(cursorParsed[0])));
        else
            return booleanBuilder.and(qProductEntity.id.lt(Integer.valueOf(cursorParsed[0])));
    }

    private OrderSpecifier getOrderSpecifier(QProductEntity qProductEntity, BaseCriteria baseCriteria) {
        OrderSpecifier orderSpecifier;
        String sort = baseCriteria.getSortBy();
        String order = baseCriteria.getOrder();

        if (StringUtils.equals(sort, SortBy.id.toString()) && StringUtils.equals(order, "asc"))
            orderSpecifier = qProductEntity.id.asc().nullsLast();
        else if (StringUtils.equals(sort, SortBy.productPrice.toString()) && StringUtils.equals(order, "desc"))
            orderSpecifier = qProductEntity.productPrice.desc().nullsLast();
        else if (StringUtils.equals(sort, SortBy.productPrice.toString()) && StringUtils.equals(order, "asc"))
            orderSpecifier = qProductEntity.productPrice.asc().nullsLast();
        else
            orderSpecifier = qProductEntity.id.desc(); // default

        return orderSpecifier;
    }
}

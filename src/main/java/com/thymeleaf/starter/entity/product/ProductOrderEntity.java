package com.thymeleaf.starter.entity.product;

import com.thymeleaf.starter.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE product_order SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Table(name = "product_order")
public class ProductOrderEntity extends BaseEntity {
    @Basic
    @Column(name = "total_price")
    private Integer totalPrice;
    @Basic
    @Column(name = "delivery_price")
    private Integer deliveryPrice = 0;
    @OneToMany(mappedBy = "productOrderEntity", cascade = CascadeType.REMOVE)
    private List<ProductOrderItemEntity> productOrderItemEntities = new ArrayList<>();

    public ProductOrderEntity(Integer totalPrice) {
        this.setTotalPrice(totalPrice);
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
        if (totalPrice == 0)
            this.deliveryPrice = 0;
        else if (totalPrice > 0 && totalPrice < 50000)
            this.deliveryPrice = 2500;
        else
            this.deliveryPrice = 0;
    }
}

package com.hh.mirishop.productmanagement.stock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hh.mirishop.productmanagement.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_stock")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

    @Id
    private Long stockId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // default_batch_fetch_size: 100 적용
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    public void update(Integer newQuantity) {
        this.quantity = newQuantity;
    }
}

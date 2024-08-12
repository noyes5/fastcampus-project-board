package com.hh.mirishop.orderpayment.order.enttiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "order_price")
    private Long orderPrice; // 주문 가격

    @Column(name = "count")
    private int count; // 주문 수량

    @Builder
    private OrderItem(Long productId, Long orderPrice, int count) {
        this.productId = productId;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void linkOrder(Order order) {
        this.order = order;
    }

    public Long getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}

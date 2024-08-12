package com.hh.mirishop.orderpayment.order.dto;

import com.hh.mirishop.orderpayment.order.enttiy.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDto {

    private Long productId;
    private Long orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        this.productId = orderItem.getProductId();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}

package com.hh.mirishop.productmanagement.stock.dto;

import com.hh.mirishop.productmanagement.product.entity.PreOrderProduct;
import com.hh.mirishop.productmanagement.stock.entity.Stock;
import lombok.Getter;

@Getter
public class StockResponse {

    private Long productId;
    private String productName;
    private int quantity;
    private String dtype;

    public StockResponse(Stock stock) {
        this.productId = stock.getStockId();
        this.productName = stock.getProduct().getName();
        this.quantity = stock.getQuantity();
        if (stock.getProduct() instanceof PreOrderProduct) {
            this.dtype = "PRE_ORDER";
        } else {
            this.dtype = "REGULAR";
        }
    }
}

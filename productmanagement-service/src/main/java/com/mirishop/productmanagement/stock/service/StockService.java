package com.hh.mirishop.productmanagement.stock.service;

import com.hh.mirishop.productmanagement.product.entity.Product;

public interface StockService {

    Integer addStock(Product product, int quantity);

    void modifyStock(Long productId, int quantity);

    void decreaseStock(Long productId, int quantity);

    void restoreStock(Long productId, int count);
}

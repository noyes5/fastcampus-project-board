package com.hh.mirishop.productmanagement.stock.service;

import com.hh.mirishop.productmanagement.stock.entity.Stock;

public interface StockQueryService {

    Stock readStock(Long productId);

    Integer readStockCount(Long productId);
}

package com.mirishop.productmanagement.stock.service;

import com.mirishop.productmanagement.common.exception.CustomException;
import com.mirishop.productmanagement.common.exception.ErrorCode;
import com.mirishop.productmanagement.stock.entity.Stock;
import com.mirishop.productmanagement.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StockQueryService {

    private final StockRepository stockRepository;

    /**
     * productId로 Stock 객체를 리턴하는 메소드
     */
    @Transactional(readOnly = true)
    public Stock readStock(Long productId) {
        return stockRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));
    }

    /**
     * productId로 재고 갯수만을 조회하는 메소드
     */
    @Transactional(readOnly = true)
    public Integer readStockCount(Long productId) {
        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.STOCK_NOT_FOUND));

        return stock.getQuantity();
    }
}

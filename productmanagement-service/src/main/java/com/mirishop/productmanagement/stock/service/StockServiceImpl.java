package com.hh.mirishop.productmanagement.stock.service;

import com.hh.mirishop.productmanagement.common.exception.ErrorCode;
import com.hh.mirishop.productmanagement.common.exception.StockException;
import com.hh.mirishop.productmanagement.config.LockConfig;
import com.hh.mirishop.productmanagement.common.lock.annotation.DistributedLock;
import com.hh.mirishop.productmanagement.product.entity.Product;
import com.hh.mirishop.productmanagement.stock.entity.Stock;
import com.hh.mirishop.productmanagement.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    /**
     * 재고 추가 메소드. product 추가 이후 실행되어야 하는 메소드
     */
    @Override
    @Transactional
    public Integer addStock(Product savedProduct, int quantity) {
        Stock stock = Stock.builder()
                .product(savedProduct)
                .quantity(quantity)
                .build();

        stockRepository.save(stock);

        return stock.getQuantity();
    }

    /**
     * 재고 수정 기능. 판매자가 재고를 수정하면 변경되는 메소드
     */
    @Override
    @Transactional
    public void modifyStock(Long productId, int quantity) {
        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));

        stock.update(quantity);
    }

    /**
     * 재고 감소 기능
     */
    @Override
    @Transactional
    @DistributedLock(lockConfig = LockConfig.TEST_LOCK)
    public void decreaseStock(Long productId, int count) {
        log.info("재고 감소 요청 productId: {}, count: {}", productId, count);

        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));

        int newQuantity = stock.getQuantity() - count;
        if (newQuantity < 0) {
            log.warn("재고 불충분 for productId: {}. 요청: {}, 가능: {}", productId, count, stock.getQuantity());
            throw new StockException(ErrorCode.STOCK_NOT_ENOUGH);
        }

        stock.update(newQuantity);
        log.info("재고 감소 완료 for productId: {}. New quantity: {}", productId, newQuantity);
    }

    /**
     * 재고 복구 기능
     */
    @Override
    @Transactional
    @DistributedLock(lockConfig = LockConfig.TEST_LOCK)
    public void restoreStock(Long productId, int count) {
        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));

        // 기존 재고에서 count만큼 복구
        int newQuantity = stock.getQuantity() + count;

        // 재고 수량 확인하여 0보다 작으면 에러
        if (newQuantity < 0) {
            throw new StockException(ErrorCode.STOCK_NOT_ENOUGH);
        }

        stock.update(newQuantity);
    }
}

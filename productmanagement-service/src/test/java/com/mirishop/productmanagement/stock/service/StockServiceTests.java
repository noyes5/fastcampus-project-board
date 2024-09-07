package com.mirishop.productmanagement.stock.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hh.mirishop.productmanagement.common.exception.ErrorCode;
import com.hh.mirishop.productmanagement.common.exception.StockException;
import com.hh.mirishop.productmanagement.product.entity.Product;
import com.hh.mirishop.productmanagement.product.entity.RegularProduct;
import com.hh.mirishop.productmanagement.stock.entity.Stock;
import com.hh.mirishop.productmanagement.stock.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
public class StockServiceTests {

    @Autowired
    private com.hh.mirishop.productmanagement.stock.service.StockService stockService;

    @MockBean
    private StockRepository stockRepository;

    private final Long productId = 1L;
    private final int initQuantity = 100;
    private Stock stock;

    @BeforeEach
    void setUp() {
        Product product = RegularProduct.builder()
                .productId(1L)
                .name("상품1")
                .content("설명입니다.")
                .price(1000L)
                .isDeleted(false)
                .build();

        stock = Stock.builder()
                .product(product)
                .quantity(initQuantity)
                .build(); // productId가 1인 상품의 초기 재고는 10개

        Mockito.when(stockRepository.findById(product.getProductId()))
                .thenReturn(Optional.of(stock));
    }

    @DisplayName("재고감소 성공 테스트")
    @Test
    void decreaseStock_SuccessTest() {
        int decreaseCount = 5; // 차감할 재고 수량
        stockService.decreaseStock(productId, decreaseCount);

        assertThat(stock.getQuantity()).isEqualTo(initQuantity - decreaseCount); // 재고가 정상적으로 차감되었는지 검증
    }

    @DisplayName("재고가 부족할 경우 실패 테스트")
    @Test
    void decreaseStock_StockNotEnoughTest() {
        int decreaseCount = 101; // 재고보다 많은 수량 차감 시도

        assertThatThrownBy(() -> stockService.decreaseStock(productId, decreaseCount))
                .isInstanceOf(StockException.class)
                .hasMessage(ErrorCode.STOCK_NOT_ENOUGH.getMessage()); // 재고 부족 예외 발생 검증
    }

    @DisplayName("잘못된 상품 재고 없음 테스트")
    @Test
    void decreaseStock_StockNotFoundTest() {
        Long wrongProductId = 2L; // 존재하지 않는 상품 ID
        Mockito.when(stockRepository.findById(wrongProductId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> stockService.decreaseStock(wrongProductId, 1))
                .isInstanceOf(StockException.class)
                .hasMessage(ErrorCode.STOCK_NOT_FOUND.getMessage());
    }

    @DisplayName("멀티스레드 환경에서 재고 차감 테스트")
    @Test
    void decreaseStockInMultiThreadEnvironment() throws InterruptedException {
        int numberOfThreads = 20;
        int decreaseCountPerThread = 1;

        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            log.info("재고 : {}", stock.getQuantity());
            service.submit(() -> {
                try {
                    stockService.decreaseStock(productId, decreaseCountPerThread);
                } catch (Exception e) {
                    log.error("재고감소 실패", e);
                }
            });
        }

        service.shutdown();
        boolean finished = service.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(finished);

        // 최종 재고 수량 검증
        assertThat(stock.getQuantity()).isEqualTo(initQuantity - numberOfThreads * decreaseCountPerThread);
    }

    @DisplayName("멀티스레드 환경에서 여러개 재고 주문하는 경우 테스트")
    @Test
    void decreaseStocksInMultiThreadEnvironment() throws InterruptedException {
        int numberOfThreads = 99;
        int decreaseCountPerThread = 1;

        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            log.info("재고 : {}", stock.getQuantity());
            service.submit(() -> {
                try {
                    stockService.decreaseStock(productId, decreaseCountPerThread);
                } catch (Exception e) {
                    log.error("재고감소 실패", e);
                }
            });
        }

        service.shutdown();
        boolean finished = service.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(finished);

        // 최종 재고 수량 검증
        assertThat(stock.getQuantity()).isEqualTo(initQuantity - numberOfThreads * decreaseCountPerThread);
    }
}
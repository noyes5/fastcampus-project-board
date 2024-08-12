package com.hh.mirishop.productmanagement.stock.controller;

import com.hh.mirishop.productmanagement.common.dto.BaseResponse;
import com.hh.mirishop.productmanagement.stock.dto.StockResponse;
import com.hh.mirishop.productmanagement.stock.entity.Stock;
import com.hh.mirishop.productmanagement.stock.service.StockQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockQueryService stockQueryService;

    /**
     * 재고를 주체로 재고 및 상품 조회할 수 있습니다.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse<StockResponse>> readStock(@PathVariable("productId") Long productId) {
        Stock productStock = stockQueryService.readStock(productId);
        StockResponse stockResponse = new StockResponse(productStock);
        return ResponseEntity.ok(new BaseResponse<>("상품 재고 정보 조회 완료", true, stockResponse));
    }
}

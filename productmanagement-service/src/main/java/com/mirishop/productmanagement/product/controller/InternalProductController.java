package com.hh.mirishop.productmanagement.product.controller;

import com.hh.mirishop.productmanagement.product.dto.ProductResponse;
import com.hh.mirishop.productmanagement.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/products")
public class InternalProductController {

    private final ProductQueryService productQueryService;

    /**
     * productId를 받아 상품 정보를 내부로 통신합니다.
     * ProductResponse에는 하나의 상품의 모든 정보가 있습니다.
     */
    @GetMapping("/{productId}")
    public ProductResponse read(@PathVariable("productId") Long productId) {
        return productQueryService.find(productId);
    }
}

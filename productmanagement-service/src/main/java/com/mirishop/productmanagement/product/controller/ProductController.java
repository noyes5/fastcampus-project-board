package com.hh.mirishop.productmanagement.product.controller;

import com.hh.mirishop.productmanagement.common.dto.BaseResponse;
import com.hh.mirishop.productmanagement.product.dto.ProductCreate;
import com.hh.mirishop.productmanagement.product.dto.ProductResponse;
import com.hh.mirishop.productmanagement.product.dto.ProductUpdate;
import com.hh.mirishop.productmanagement.product.service.ProductQueryService;
import com.hh.mirishop.productmanagement.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductQueryService productQueryService;

    /**
     * 상품을 전체 조회할 수 있습니다.
     * Page객체로 제공합니다.
     */
    @GetMapping
    public ResponseEntity<BaseResponse<Page<ProductResponse>>> readAll(Pageable pageable) {
        Page<ProductResponse> products = productQueryService.findProducts(pageable);
        return ResponseEntity.ok(new BaseResponse<>("상품 전체 조회 성공", true, products));
    }

    /**
     * productId를 받아 상품 하나의 정보를 보여줍니다.
     * ProductResponse에는 상품의 모든 정보가 있습니다.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse<ProductResponse>> read(@PathVariable("productId") Long productId) {
        ProductResponse productResponse = productQueryService.find(productId);
        return ResponseEntity.ok(new BaseResponse<>("상품 조회 성공", true, productResponse));
    }

    /**
     * 상품 내용을 받아 상품을 추가할 수 있습니다.
     */
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> create(@Valid @RequestBody ProductCreate productCreate) {
        productService.createProductAndStock(productCreate);

        return ResponseEntity.ok(new BaseResponse<>("상품 생성 성공", true, null));
    }

    /**
     * 상품 내용을 받아 상품을 수정할 수 있습니다.
     */
    @PutMapping("/{productId}")
    public ResponseEntity<BaseResponse<Void>> update(@PathVariable("productId") Long productId,
                                                     @Valid @RequestBody ProductUpdate productUpdate) {
        productService.update(productId, productUpdate);

        return ResponseEntity.ok(new BaseResponse<>("상품이 업데이트되었습니다.", true, null));
    }

    /**
     * 상품 내용을 받아 상품을 수정할 수 있습니다.
     */
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);

        return ResponseEntity.ok(new BaseResponse<>("상품이 삭제되었습니다.", true, null));
    }
}

package com.mirishop.productmanagement.product.service;

import com.mirishop.productmanagement.common.exception.CustomException;
import com.mirishop.productmanagement.common.exception.ErrorCode;
import com.mirishop.productmanagement.product.dto.ProductResponse;
import com.mirishop.productmanagement.product.entity.Product;
import com.mirishop.productmanagement.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductRepository productRepository;

    /**
     * 전체 상품 조회하는 메소드
     */
    @Transactional(readOnly = true)
    public Page<ProductResponse> findProducts(Pageable pageable) {
        Page<Product> products = productRepository.findByIsDeletedFalse(pageable);

        return products.map(ProductResponse::new);
    }


    /**
     * 상품 한 개 조회하는 메소드
     */
    @Transactional
    public ProductResponse find(long productId) {
        Product product = productRepository.findByProductIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return new ProductResponse(product);
    }
}

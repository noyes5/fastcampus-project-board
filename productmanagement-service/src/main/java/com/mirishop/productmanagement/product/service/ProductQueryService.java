package com.hh.mirishop.productmanagement.product.service;

import com.hh.mirishop.productmanagement.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryService {

    Page<ProductResponse> findProducts(Pageable pageable);

    ProductResponse find(long productId);
}

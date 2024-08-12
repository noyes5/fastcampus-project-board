package com.hh.mirishop.productmanagement.product.service;

import com.hh.mirishop.productmanagement.product.dto.ProductCreate;
import com.hh.mirishop.productmanagement.product.dto.ProductUpdate;

public interface ProductService {

    Long createProductAndStock(ProductCreate productCreate);

    void update(Long productId, ProductUpdate productUpdate);

    void delete(Long productId);
}

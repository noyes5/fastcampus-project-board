package com.hh.mirishop.productmanagement.product.service;

import com.hh.mirishop.productmanagement.common.exception.ErrorCode;
import com.hh.mirishop.productmanagement.common.exception.ProductException;
import com.hh.mirishop.productmanagement.product.dto.ProductCreate;
import com.hh.mirishop.productmanagement.product.dto.ProductUpdate;
import com.hh.mirishop.productmanagement.product.entity.PreOrderProduct;
import com.hh.mirishop.productmanagement.product.entity.Product;
import com.hh.mirishop.productmanagement.product.entity.RegularProduct;
import com.hh.mirishop.productmanagement.product.repository.ProductRepository;
import com.hh.mirishop.productmanagement.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SoftDelete;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StockService stockService;

    /**
     * 상품을 생성하는 메소드
     */
    @Override
    @Transactional
    public Long createProductAndStock(ProductCreate productCreate) {
        // ReservationTime을 가지는 경우와 아닌 경우로 나누어 Product 생성
        Product product = prepareProduct(productCreate);
        Product savedProduct = productRepository.save(product);

        // stock에 재고 생성
        stockService.addStock(savedProduct, productCreate.getQuantity());

        return savedProduct.getProductId();
    }

    /**
     * 상품을 수정하는 메소드
     */
    @Override
    @Transactional
    public void update(Long productId, ProductUpdate productUpdate) {
        Product product = findProductById(productId);
        // 예약 시간은 중요한 기능이므로 다른 엔드포인트에서 수정 가능
        product.update(productUpdate.getName(), productUpdate.getContent(), productUpdate.getPrice());

        // stock 재고 수정
        stockService.modifyStock(productId, productUpdate.getQuantity());
    }

    /**
     * 상품을 삭제하는 메소드
     */
    @Override
    @SoftDelete
    @Transactional
    public void delete(Long productId) {
        Product product = findProductById(productId);

        product.delete(true);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    private Product findProductById(Long productId) {
        return productRepository.findByProductIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
    }


    private Product prepareProduct(ProductCreate productCreate) {
        if (productCreate.getReservationTime() != null) {
            return createPreOrderProduct(productCreate);
        }
        return createRegularProduct(productCreate);
    }

    private Product createPreOrderProduct(ProductCreate productCreate) {
        return PreOrderProduct.builder()
                .name(productCreate.getName())
                .content(productCreate.getContent())
                .price(productCreate.getPrice())
                .isDeleted(false)
                .build();
    }

    private Product createRegularProduct(ProductCreate productCreate) {
        return RegularProduct.builder()
                .name(productCreate.getName())
                .content(productCreate.getContent())
                .price(productCreate.getPrice())
                .isDeleted(false)
                .build();
    }
}

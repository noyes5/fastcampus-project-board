package com.mirishop.productmanagement.product.repository;

import com.mirishop.productmanagement.product.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 전체 상품 조회
    Page<Product> findByIsDeletedFalse(Pageable pageable);

    // 단일 상품 조회
    Optional<Product> findByProductIdAndIsDeletedFalse(Long productId);
}

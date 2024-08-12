package com.hh.mirishop.productmanagement.stock.repository;

import com.hh.mirishop.productmanagement.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

//    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT) // 낙관적 락 우선 적용
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Stock> findById(Long id);
}

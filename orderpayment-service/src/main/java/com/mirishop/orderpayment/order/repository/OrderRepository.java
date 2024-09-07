package com.mirishop.orderpayment.order.repository;

import com.mirishop.orderpayment.order.enttiy.Order;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    //    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.orderId = :orderId")
    @EntityGraph(attributePaths = {"orderItems"})
    Optional<Order> findOrderWithOrderItemsByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT o FROM Order o WHERE o.memberNumber = :memberNumber")
    Page<Order> findAllByMemberNumber(@Param("memberNumber") Long memberNumber, Pageable pageable);

    Optional<Order> findByOrderIdAndMemberNumber(Long orderId, Long memberNumber);
}


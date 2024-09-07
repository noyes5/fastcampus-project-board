//package com.mirishop.orderpayment.order.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.mirishop.orderpayment.common.exception.CustomException;
//import com.mirishop.orderpayment.common.exception.ErrorCode;
//import com.mirishop.orderpayment.order.enttiy.Order;
//import com.mirishop.orderpayment.order.repository.OrderRepository;
//import org.hibernate.Hibernate;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//class OrderRepositoryTest {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Test
//    @Transactional
//    void testOrderEntityIsProxyOrNot() {
//        Long orderId = 1L; // 가정: 데이터베이스에 이미 존재하는 orderId
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
//
//        boolean isInitialized = Hibernate.isInitialized(order.getOrderItems());
//
//        assertThat(isInitialized).isFalse();
//
//        order.getOrderItems().size();
//        isInitialized = Hibernate.isInitialized(order.getOrderItems());
//
//        assertThat(isInitialized).isTrue();
//    }
//}

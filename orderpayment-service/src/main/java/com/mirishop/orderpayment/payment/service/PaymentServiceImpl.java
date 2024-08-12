package com.hh.mirishop.orderpayment.payment.service;

import com.hh.mirishop.orderpayment.common.exception.ErrorCode;
import com.hh.mirishop.orderpayment.common.exception.OrderException;
import com.hh.mirishop.orderpayment.order.enttiy.Order;
import com.hh.mirishop.orderpayment.order.repository.OrderRepository;
import com.hh.mirishop.orderpayment.payment.dto.PaymentResponse;
import com.hh.mirishop.orderpayment.payment.entity.Payment;
import com.hh.mirishop.orderpayment.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    /**
     * 결제를 생성하는 메소드
     */
    @Override
    @Transactional
    public PaymentResponse createPayment(Long orderId, Long currentMemberNumber) {
        // 주문 여부 조회
        Order savedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
        Payment payment = Payment.builder()
                .memberNumber(currentMemberNumber)
                .isDeleted(false)
                .build();

        paymentRepository.save(payment);
        savedOrder.addPayment(payment);

        return new PaymentResponse(payment);
    }
}

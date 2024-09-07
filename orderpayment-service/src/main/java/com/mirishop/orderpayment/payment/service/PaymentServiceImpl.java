package com.mirishop.orderpayment.payment.service;

import com.mirishop.orderpayment.common.exception.CustomException;
import com.mirishop.orderpayment.common.exception.ErrorCode;
import com.mirishop.orderpayment.order.enttiy.Order;
import com.mirishop.orderpayment.order.repository.OrderRepository;
import com.mirishop.orderpayment.payment.dto.PaymentResponse;
import com.mirishop.orderpayment.payment.entity.Payment;
import com.mirishop.orderpayment.payment.repository.PaymentRepository;
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
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        Payment payment = Payment.builder()
                .memberNumber(currentMemberNumber)
                .isDeleted(false)
                .build();

        paymentRepository.save(payment);
        savedOrder.addPayment(payment);

        return new PaymentResponse(payment);
    }
}

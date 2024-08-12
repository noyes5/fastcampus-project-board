package com.hh.mirishop.orderpayment.payment.dto;

import com.hh.mirishop.orderpayment.payment.entity.Payment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentResponse {

    private Long orderId;
    private Long paymentId;
    private LocalDateTime createdAt;

    public PaymentResponse(Payment payment) {
        this.orderId = payment.getOrder().getOrderId();
        this.paymentId = payment.getPaymentId();
        this.createdAt = payment.getCreatedAt();
    }
}

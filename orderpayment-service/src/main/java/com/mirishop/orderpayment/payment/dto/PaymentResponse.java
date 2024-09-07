package com.mirishop.orderpayment.payment.dto;

import com.mirishop.orderpayment.payment.entity.Payment;
import java.time.LocalDateTime;
import lombok.Getter;

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

package com.mirishop.orderpayment.payment.service;

import com.mirishop.orderpayment.payment.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(Long orderId, Long currentMemberNumber);
}

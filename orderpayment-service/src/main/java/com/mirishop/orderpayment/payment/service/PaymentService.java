package com.hh.mirishop.orderpayment.payment.service;

import com.hh.mirishop.orderpayment.payment.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(Long orderId, Long currentMemberNumber);
}

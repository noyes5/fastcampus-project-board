package com.hh.mirishop.orderpayment.payment.controller;

import com.hh.mirishop.orderpayment.common.dto.BaseResponse;
import com.hh.mirishop.orderpayment.payment.dto.PaymentCreate;
import com.hh.mirishop.orderpayment.payment.dto.PaymentResponse;
import com.hh.mirishop.orderpayment.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 정보를 받아 PaymentResponse를 리턴합니다.
     * PaymentResponse에는 orderId, paymentId, 결제 시간이 있습니다.
     */
    @PostMapping
    public ResponseEntity<BaseResponse<PaymentResponse>> process(@RequestBody PaymentCreate paymentCreate,
                                                                 @RequestHeader(name = "X-MEMBER-NUMBER") Long currentMemberNumber) {
        PaymentResponse payment = paymentService.createPayment(paymentCreate.getOrderId(), currentMemberNumber);

        return ResponseEntity.ok(BaseResponse.of("결제 성공", true, payment));
    }
}

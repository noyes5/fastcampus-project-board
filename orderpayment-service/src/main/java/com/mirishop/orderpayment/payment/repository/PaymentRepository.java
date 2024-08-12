package com.hh.mirishop.orderpayment.payment.repository;

import com.hh.mirishop.orderpayment.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findById(Long paymentId);

    Page<Payment> findAll(Pageable pageable);
}

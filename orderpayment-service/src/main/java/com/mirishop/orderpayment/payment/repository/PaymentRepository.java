package com.mirishop.orderpayment.payment.repository;

import com.mirishop.orderpayment.payment.entity.Payment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findById(Long paymentId);

    Page<Payment> findAll(Pageable pageable);
}

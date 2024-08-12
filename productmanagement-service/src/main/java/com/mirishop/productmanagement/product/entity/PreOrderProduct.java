package com.hh.mirishop.productmanagement.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 예약 주문 상품
 */
@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@DiscriminatorValue("PRE_ORDER")
public class PreOrderProduct extends Product {

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;
}

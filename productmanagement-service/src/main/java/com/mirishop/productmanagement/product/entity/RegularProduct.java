package com.hh.mirishop.productmanagement.product.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 일반상품
 */
@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@DiscriminatorValue("REGULAR")
public class RegularProduct extends Product {

}

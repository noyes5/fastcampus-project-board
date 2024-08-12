package com.hh.mirishop.productmanagement.product.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductCreate {

    @NotBlank(message = "상품 이름을 입력해야 합니다.")
    private String name;

    @NotBlank(message = "상품 내용을 입력해야 합니다.")
    private String content;

    @NotBlank(message = "상품 가격을 입력해야 합니다.")
    private Long price;

    @NotBlank(message = "상품 수량을 입력해야 합니다.")
    private Integer quantity;

    private LocalDateTime reservationTime;
}
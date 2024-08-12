package com.hh.mirishop.orderpayment.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponse {

    private Long productId;
    private String content;
    private Long price;
    private Integer quantity;
}

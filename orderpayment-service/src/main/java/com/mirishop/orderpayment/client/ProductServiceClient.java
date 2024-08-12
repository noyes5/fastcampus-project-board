package com.hh.mirishop.orderpayment.client;

import com.hh.mirishop.orderpayment.client.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "productmanagement-service", url = "${external.productmanagement-service.url}")
public interface ProductServiceClient {

    @GetMapping("/api/v1/internal/products/{productId}")
    ProductResponse getProductById(@PathVariable("productId") Long productId);

    @PostMapping("/api/v1/internal/stocks/decrease")
    void decreaseStock(@RequestParam("productId") Long productId, @RequestParam("count") int count);

    @PostMapping("/api/v1/internal/stocks/restore")
    void restoreStock(@RequestParam("productId") Long productId, @RequestParam("count") int count);
}

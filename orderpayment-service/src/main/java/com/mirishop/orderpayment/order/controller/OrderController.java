package com.hh.mirishop.orderpayment.order.controller;


import com.hh.mirishop.orderpayment.common.dto.BaseResponse;
import com.hh.mirishop.orderpayment.order.dto.OrderAddressDto;
import com.hh.mirishop.orderpayment.order.dto.OrderCreate;
import com.hh.mirishop.orderpayment.order.dto.OrderDto;
import com.hh.mirishop.orderpayment.order.enttiy.Order;
import com.hh.mirishop.orderpayment.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * - 주문 생성 로직 순서
 * 1. 주문버튼 누르면 유저가 재고 소유권을 가지게되며 주문 정보 생성(주문상태 PAYMENT_WAITING)
 * 2. 주소 정보 입력하여 주문 엔티티 수정.(주문상태는 계속 PAYMENT_WAITING)
 * 3. 결제를 하고 나서 주문 완료 메소드를 통해 최종 주문 완료(주문상태 COMPLETE_ORDER)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성 정보를 받아 주문을 생성합니다.
     */
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody OrderCreate orderCreate,
                                                     @RequestHeader(name = "X-MEMBER-NUMBER") Long currentMemberNumber) {
        orderService.createOrder(orderCreate, currentMemberNumber);
        return ResponseEntity.ok(BaseResponse.of("결재 준비중 주문 생성 완료", true, null));
    }

    /**
     * orderId를 받아 주문을 조회합니다.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<BaseResponse<OrderDto>> getOrder(@PathVariable("orderId") Long orderId,
                                                           @RequestHeader(name = "X-MEMBER-NUMBER") Long currentMemberNumber) {
        OrderDto orderByMemberNumber = orderService.findOrderByMemberNumber(orderId);
        return ResponseEntity.ok(BaseResponse.of("주문 단건 조회 완료", true, orderByMemberNumber));
    }

    /**
     * orderId와 주소 정보를 받아 주문을 갱신합니다.
     */
    @PutMapping("/{orderId}/address")
    public ResponseEntity<BaseResponse<Void>> addAddressToOrder(@PathVariable("orderId") Long orderId,
                                                                @RequestBody OrderAddressDto orderAddress) {
        orderService.addAddressToOrder(orderId, orderAddress);
        return ResponseEntity.ok(BaseResponse.of("주문에 주소 입력 완료", true, null));
    }

    /**
     * orderId를 받아 주문을 최종 완료합니다.
     */
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<BaseResponse<Void>> cancelOrder(@PathVariable("orderId") Long orderId,
                                                          @RequestHeader(name = "X-MEMBER-NUMBER") Long currentMemberNumber) {
        orderService.completeOrder(orderId, currentMemberNumber);
        return ResponseEntity.ok(BaseResponse.of("최종 주문 완료", true, null));
    }

    /**
     * orderId를 받아 주문을 취소합니다.
     */
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<BaseResponse<Void>> completeOrder(@PathVariable("orderId") Long orderId,
                                                            @RequestHeader(name = "X-MEMBER-NUMBER") Long currentMemberNumber) {
        orderService.cancelOrder(orderId, currentMemberNumber);
        return ResponseEntity.ok(BaseResponse.of("주문 취소 완료", true, null));
    }

    /**
     * 유저의 모든 주문에 대한 정보를 조회합니다.
     * Page객체를 리턴합니다.
     */
    @GetMapping
    public ResponseEntity<BaseResponse<Page<Order>>> findAllOrders(
            @RequestHeader(name = "X-MEMBER-NUMBER") Long currentMemberNumber,
            Pageable pageable) {
        Page<Order> orders = orderService.findAllOrdersByMemberNumber(currentMemberNumber, pageable);
        return ResponseEntity.ok(BaseResponse.of("전체 주문 조회", true, orders));
    }
}

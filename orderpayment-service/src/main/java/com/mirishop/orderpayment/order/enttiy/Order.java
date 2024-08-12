package com.hh.mirishop.orderpayment.order.enttiy;

import com.hh.mirishop.orderpayment.order.domain.OrderStatus;
import com.hh.mirishop.orderpayment.payment.entity.Payment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "member_number", nullable = false)
    private Long memberNumber;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private Payment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate; // 주문시간

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태

    public static Order create(Long memberNumber, OrderItem... orderItems) {
        Order order = new Order();
        order.memberNumber = memberNumber;
        order.status = OrderStatus.PAYMENT_WAITING;
        order.orderDate = LocalDateTime.now();
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    /**
     * 상품 추가
     */
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.linkOrder(this);
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        this.status = OrderStatus.CANCEL;
    }

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    /**
     * 주소 추가
     */
    public void addAddress(String address) {
        this.address = address;
    }

    /**
     * 결제 추가
     */
    public void addPayment(Payment payment) {
        this.payment = payment;
        payment.linkOrder(this);
    }

    /**
     * 최종 주문 완료
     */
    public void complete() {
        this.status = OrderStatus.COMPLETE_ORDER;
    }
}

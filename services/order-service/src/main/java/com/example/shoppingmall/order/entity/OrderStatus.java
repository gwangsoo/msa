package com.example.shoppingmall.order.entity;

public enum OrderStatus {
    CREATED,        // 주문 생성됨
    PAYMENT_PENDING, // 결제 대기중
    PAID,           // 결제 완료
    SHIPPING,       // 배송중
    DELIVERED,      // 배송 완료
    CANCELED        // 주문 취소
}

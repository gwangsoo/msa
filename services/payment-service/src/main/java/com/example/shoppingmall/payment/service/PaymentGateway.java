package com.example.shoppingmall.payment.service;

import com.example.shoppingmall.payment.dto.OrderDto;

public interface PaymentGateway {
    boolean processPayment(OrderDto orderDto);
    String getCountryCode();
}

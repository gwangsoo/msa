package com.example.shoppingmall.payment.service;

import com.example.shoppingmall.payment.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KoreanPaymentGateway implements PaymentGateway {

    @Override
    public boolean processPayment(OrderDto orderDto) {
        log.info("Processing payment for order {} with Korean PG", orderDto.getOrderId());
        // PG사 연동 로직
        return true; // Assume payment is always successful
    }

    @Override
    public String getCountryCode() {
        return "KR";
    }
}

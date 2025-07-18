package com.example.shoppingmall.payment.service;

import com.example.shoppingmall.payment.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    public void handleSuccessfulPayment(OrderDto orderDto) {
        log.info("Payment successful for order: {}", orderDto.getOrderId());
        // Update payment status in DB, etc.
        kafkaTemplate.send("payment-completed", orderDto);
    }

    public void handleFailedPayment(OrderDto orderDto) {
        log.error("Payment failed for order: {}", orderDto.getOrderId());
        // Update payment status in DB, etc.
        kafkaTemplate.send("payment-failed", orderDto);
    }
}

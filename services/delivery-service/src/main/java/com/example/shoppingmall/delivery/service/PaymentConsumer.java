package com.example.shoppingmall.delivery.service;

import com.example.shoppingmall.delivery.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final DeliveryService deliveryService;

    @KafkaListener(topics = "payment-completed", groupId = "delivery-group")
    public void consume(OrderDto orderDto) {
        log.info("Received payment completed event: {}", orderDto);
        deliveryService.startDelivery(orderDto);
    }
}

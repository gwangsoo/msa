package com.example.shoppingmall.order.service;

import com.example.shoppingmall.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private static final String TOPIC = "order-created";
    private final KafkaTemplate<String, OrderDto.OrderResponse> kafkaTemplate;

    public void sendOrderCreatedEvent(OrderDto.OrderResponse orderResponse) {
        log.info("Sending order created event to Kafka: {}", orderResponse);
        this.kafkaTemplate.send(TOPIC, orderResponse);
    }
}

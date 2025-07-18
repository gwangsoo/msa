package com.example.shoppingmall.delivery.service;

import com.example.shoppingmall.delivery.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryService {

    public void startDelivery(OrderDto orderDto) {
        log.info("Starting delivery for order: {}", orderDto.getOrderId());
        // Create Delivery entity, call external delivery API, etc.
    }
}

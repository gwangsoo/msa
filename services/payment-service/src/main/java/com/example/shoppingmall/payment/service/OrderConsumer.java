package com.example.shoppingmall.payment.service;

import com.example.shoppingmall.payment.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final Map<String, PaymentGateway> paymentGateways;
    private final PaymentService paymentService;

    public OrderConsumer(List<PaymentGateway> gateways, PaymentService paymentService) {
        this.paymentGateways = gateways.stream()
                .collect(Collectors.toUnmodifiableMap(PaymentGateway::getCountryCode, Function.identity()));
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order-created", groupId = "payment-group")
    public void consume(OrderDto orderDto) {
        log.info("Received order created event: {}", orderDto);

        // In a real app, you would get the country code from the user's profile or order details
        String countryCode = "KR";

        PaymentGateway gateway = paymentGateways.get(countryCode);
        if (gateway == null) {
            log.error("No payment gateway found for country code: {}", countryCode);
            // Handle error, maybe send to a dead-letter queue
            return;
        }

        boolean paymentSuccess = gateway.processPayment(orderDto);

        if (paymentSuccess) {
            paymentService.handleSuccessfulPayment(orderDto);
        } else {
            paymentService.handleFailedPayment(orderDto);
        }
    }
}

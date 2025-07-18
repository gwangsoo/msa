package com.example.shoppingmall.order.controller;

import com.example.shoppingmall.order.dto.OrderDto;
import com.example.shoppingmall.order.entity.Order;
import com.example.shoppingmall.order.repository.OrderRepository;
import com.example.shoppingmall.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository; // For simple queries

    @PostMapping
    public Mono<ResponseEntity<Order>> createOrder(@RequestBody OrderDto.OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest)
                .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order))
                .onErrorResume(IllegalStateException.class,
                        e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

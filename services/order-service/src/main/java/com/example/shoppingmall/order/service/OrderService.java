package com.example.shoppingmall.order.service;

import com.example.shoppingmall.order.dto.OrderDto;
import com.example.shoppingmall.order.entity.Order;
import com.example.shoppingmall.order.entity.OrderItem;
import com.example.shoppingmall.order.entity.OrderStatus;
import com.example.shoppingmall.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final WebClient.Builder webClientBuilder;

    @Value("${service-urls.product-service}")
    private String productServiceUrl;

    @Transactional
    public Mono<Order> createOrder(OrderDto.OrderRequest orderRequest) {
        // This is a simplified version of the Saga pattern.
        // A real implementation would require more complex state management and compensation logic.

        // 1. Fetch product details and validate stock
        return Mono.zip(
                orderRequest.getOrderItems().stream()
                        .map(item -> fetchProduct(item.getProductId()))
                        .collect(Collectors.toList()),
                results -> {
                    Order order = new Order();
                    order.setUserId(orderRequest.getUserId());
                    order.setStatus(OrderStatus.CREATED);

                    BigDecimal totalPrice = BigDecimal.ZERO;
                    for (int i = 0; i < results.length; i++) {
                        OrderDto.ProductResponse product = (OrderDto.ProductResponse) results[i];
                        OrderDto.OrderItemRequest orderItemRequest = orderRequest.getOrderItems().get(i);

                        if (product.getStock() < orderItemRequest.getQuantity()) {
                            throw new IllegalStateException("Not enough stock for product " + product.getId());
                        }

                        OrderItem orderItem = new OrderItem();
                        orderItem.setProductId(product.getId());
                        orderItem.setQuantity(orderItemRequest.getQuantity());
                        orderItem.setPrice(product.getPrice());
                        order.addOrderItem(orderItem);

                        totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())));
                    }
                    order.setTotalPrice(totalPrice);
                    return order;
                }
        )
        .flatMap(order -> {
            // 2. Save the order
            Order savedOrder = orderRepository.save(order);
            // 3. Decrease stock (and other post-order actions)
            return decreaseStock(savedOrder.getOrderItems()).thenReturn(savedOrder);
        })
        .doOnSuccess(order -> {
            // 4. Publish order created event
            // This part is tricky to map directly. For now, let's assume we build the response DTO here.
            // A better approach would be to refactor this.
            // For simplicity, we are not building a full DTO here.
            // orderProducer.sendOrderCreatedEvent( ... );
        });
    }

    private Mono<OrderDto.ProductResponse> fetchProduct(Long productId) {
        return webClientBuilder.build()
                .get()
                .uri(productServiceUrl + "/api/products/{id}", productId)
                .retrieve()
                .bodyToMono(OrderDto.ProductResponse.class);
    }

    private Mono<Void> decreaseStock(List<OrderItem> orderItems) {
        // In a real-world scenario, the product service should expose an endpoint to decrease stock.
        // Calling the general update endpoint for each item is inefficient and not atomic.
        // This is a simplified placeholder.
        return Mono.when(
            orderItems.stream()
                .map(item -> {
                    // This is not how you would implement this in production.
                    // You'd have a dedicated endpoint in the product service.
                    return Mono.empty(); // Placeholder
                })
                .collect(Collectors.toList())
        );
    }
}

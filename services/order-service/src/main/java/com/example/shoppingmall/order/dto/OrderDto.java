package com.example.shoppingmall.order.dto;

import com.example.shoppingmall.order.entity.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    @Data
    public static class OrderRequest {
        private Long userId;
        private List<OrderItemRequest> orderItems;
    }

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }

    @Data
    public static class OrderResponse {
        private Long orderId;
        private Long userId;
        private List<OrderItemResponse> orderItems;
        private BigDecimal totalPrice;
        private OrderStatus status;
        private LocalDateTime orderDate;
    }

    @Data
    public static class OrderItemResponse {
        private Long productId;
        private Integer quantity;
        private BigDecimal price;
    }

    // For communication with Product Service
    @Getter
    @Setter
    public static class ProductResponse {
        private Long id;
        private String name;
        private BigDecimal price;
        private Integer stock;
        private Long version;
    }
}

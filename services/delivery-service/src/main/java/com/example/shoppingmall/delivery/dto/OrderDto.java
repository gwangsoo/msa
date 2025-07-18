package com.example.shoppingmall.delivery.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long orderId;
    private Long userId;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime orderDate;

    @Data
    public static class OrderItemDto {
        private Long productId;
        private Integer quantity;
        private BigDecimal price;
    }
}

package com.example.shoppingmall.adminbff.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DashboardDto {

    @Data
    @Builder
    public static class DashboardResponse {
        private Long newUserCount;
        private Long totalOrderCount;
        private BigDecimal totalSales;
        private List<SimpleProductResponse> popularProducts;
        private List<SimpleOrderResponse> recentOrders;
    }

    @Data
    public static class SimpleProductResponse {
        private Long id;
        private String name;
        private Integer stock;
    }

    @Data
    public static class SimpleOrderResponse {
        private Long orderId;
        private Long userId;
        private BigDecimal totalPrice;
        private String status;
        private LocalDateTime orderDate;
    }

    @Data
    public static class UserResponse {
        private Long id;
        private String username;
        private LocalDateTime createdAt;
    }
}

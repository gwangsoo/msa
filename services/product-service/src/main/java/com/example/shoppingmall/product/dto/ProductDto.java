package com.example.shoppingmall.product.dto;

import lombok.Data;
import java.math.BigDecimal;

public class ProductDto {

    @Data
    public static class ProductCreateRequest {
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String category;
    }

    @Data
    public static class ProductUpdateRequest {
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String category;
        private Long version;
    }

    @Data
    public static class ProductResponse {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String category;
        private String imageUrl;
        private Long version;
    }
}

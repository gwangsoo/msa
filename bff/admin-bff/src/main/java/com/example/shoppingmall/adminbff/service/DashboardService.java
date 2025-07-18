package com.example.shoppingmall.adminbff.service;

import com.example.shoppingmall.adminbff.dto.DashboardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final WebClient.Builder webClientBuilder;

    @Value("${service-urls.user-service}")
    private String userServiceUrl;

    @Value("${service-urls.product-service}")
    private String productServiceUrl;

    @Value("${service-urls.order-service}")
    private String orderServiceUrl;

    public Mono<DashboardDto.DashboardResponse> getDashboardData() {
        WebClient webClient = webClientBuilder.build();

        // These endpoints do not exist yet in the services.
        // We are assuming they will be created.
        Mono<List<DashboardDto.UserResponse>> usersMono = webClient.get()
                .uri(userServiceUrl + "/api/users?limit=5") // Assuming a 'limit' param exists
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DashboardDto.UserResponse>>() {});

        Mono<List<DashboardDto.SimpleProductResponse>> productsMono = webClient.get()
                .uri(productServiceUrl + "/api/products?sort=popularity&limit=5") // Assuming sorting/limit exists
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DashboardDto.SimpleProductResponse>>() {});

        Mono<List<DashboardDto.SimpleOrderResponse>> ordersMono = webClient.get()
                .uri(orderServiceUrl + "/api/orders?sort=recent&limit=5") // Assuming sorting/limit exists
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DashboardDto.SimpleOrderResponse>>() {});

        return Mono.zip(usersMono, productsMono, ordersMono)
                .map(tuple -> {
                    List<DashboardDto.UserResponse> users = tuple.getT1();
                    List<DashboardDto.SimpleProductResponse> products = tuple.getT2();
                    List<DashboardDto.SimpleOrderResponse> orders = tuple.getT3();

                    // Dummy aggregation logic
                    long newUserCount = users.size();
                    long totalOrderCount = orders.size();
                    BigDecimal totalSales = orders.stream()
                                                  .map(DashboardDto.SimpleOrderResponse::getTotalPrice)
                                                  .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return DashboardDto.DashboardResponse.builder()
                            .newUserCount(newUserCount)
                            .totalOrderCount(totalOrderCount)
                            .totalSales(totalSales)
                            .popularProducts(products)
                            .recentOrders(orders)
                            .build();
                });
    }
}

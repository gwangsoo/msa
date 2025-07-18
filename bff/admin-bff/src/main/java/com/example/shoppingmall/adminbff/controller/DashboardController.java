package com.example.shoppingmall.adminbff.controller;

import com.example.shoppingmall.adminbff.dto.DashboardDto;
import com.example.shoppingmall.adminbff.service.DashboardService;
import com.example.shoppingmall.common.auth.RequiredRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @RequiredRole({"ADMIN"})
    public Mono<DashboardDto.DashboardResponse> getDashboard() {
        return dashboardService.getDashboardData();
    }
}

package com.example.shoppingmall.common.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequiredRole requiredRole = handlerMethod.getMethodAnnotation(RequiredRole.class);

            if (requiredRole != null) {
                // 1. Get user roles from JWT token (e.g., from SecurityContextHolder)
                // This is a placeholder. Actual implementation will depend on Spring Security setup.
                List<String> userRoles = getUserRolesFromToken();

                // 2. Check if the user has any of the required roles
                boolean hasPermission = Arrays.stream(requiredRole.value())
                                              .anyMatch(userRoles::contains);

                if (!hasPermission) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return false;
                }
            }
        }
        return true;
    }

    private List<String> getUserRolesFromToken() {
        // Placeholder: In a real application, you would extract this from the SecurityContext
        // which is populated by Spring Security based on the JWT token from Keycloak.
        // For now, returning a dummy list.
        return List.of("USER"); // or "ADMIN", "GUEST", etc.
    }
}

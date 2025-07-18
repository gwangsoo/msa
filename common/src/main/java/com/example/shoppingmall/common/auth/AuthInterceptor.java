package com.example.shoppingmall.common.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequiredRole requiredRole = handlerMethod.getMethodAnnotation(RequiredRole.class);

            if (requiredRole != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication == null || !authentication.isAuthenticated()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    return false;
                }

                List<String> userRoles = getUserRolesFromAuthentication(authentication);

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

    private List<String> getUserRolesFromAuthentication(Authentication authentication) {
        // Keycloak roles are often prefixed with 'ROLE_'. We might need to adjust this based on the actual token content.
        // For now, we assume the authority string is the role name.
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role)
                .collect(Collectors.toList());
    }
}

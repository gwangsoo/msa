package com.example.shoppingmall.user.controller;

import com.example.shoppingmall.common.auth.RequiredRole;
import com.example.shoppingmall.user.entity.User;
import com.example.shoppingmall.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // This is just a placeholder to show how role-based access control would work.
    @GetMapping
    @RequiredRole({"ADMIN"})
    public List<User> getAllUsers() {
        // In a real app, you'd have a method in your service to get all users.
        // For now, returning an empty list.
        return List.of();
    }
}

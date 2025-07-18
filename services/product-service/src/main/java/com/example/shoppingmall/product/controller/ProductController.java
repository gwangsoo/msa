package com.example.shoppingmall.product.controller;

import com.example.shoppingmall.common.auth.RequiredRole;
import com.example.shoppingmall.product.dto.ProductDto;
import com.example.shoppingmall.product.service.FileStorageService;
import com.example.shoppingmall.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final FileStorageService fileStorageService;

    @PostMapping
    @RequiredRole({"ADMIN"})
    public ResponseEntity<ProductDto.ProductResponse> createProduct(@RequestBody ProductDto.ProductCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto.ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    @RequiredRole({"ADMIN"})
    public ResponseEntity<ProductDto.ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductDto.ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @RequiredRole({"ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/{id}/image")
    @RequiredRole({"ADMIN"})
    public ResponseEntity<ProductDto.ProductResponse> uploadProductImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String objectName = fileStorageService.storeFile(file);
        ProductDto.ProductResponse response = productService.uploadProductImage(id, objectName);
        return ResponseEntity.ok(response);
    }
}

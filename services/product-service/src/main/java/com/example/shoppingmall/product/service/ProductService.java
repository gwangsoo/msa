package com.example.shoppingmall.product.service;

import com.example.shoppingmall.product.dto.ProductDto;
import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.product.mapper.ProductMapper;
import com.example.shoppingmall.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final FileStorageService fileStorageService;

    public ProductDto.ProductResponse createProduct(ProductDto.ProductCreateRequest request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductDto.ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        ProductDto.ProductResponse response = productMapper.toResponse(product);
        if (product.getImageUrl() != null) {
            response.setImageUrl(fileStorageService.getPresignedUrl(product.getImageUrl()));
        }
        return response;
    }

    public ProductDto.ProductResponse updateProduct(Long id, ProductDto.ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        // Optimistic Lock check
        if (!product.getVersion().equals(request.getVersion())) {
            throw new OptimisticLockException("Product has been updated by another transaction");
        }

        productMapper.updateFromDto(request, product);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDto.ProductResponse uploadProductImage(Long id, String objectName) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        product.setImageUrl(objectName);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }
}

package com.example.shoppingmall.product.mapper;

import com.example.shoppingmall.product.dto.ProductDto;
import com.example.shoppingmall.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toEntity(ProductDto.ProductCreateRequest createRequest);

    ProductResponse toResponse(Product product);

    void updateFromDto(ProductDto.ProductUpdateRequest updateRequest, @MappingTarget Product product);
}

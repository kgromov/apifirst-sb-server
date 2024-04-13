package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.ProductCreateDto;
import org.kgromov.apifirst.model.ProductDto;
import org.kgromov.apifirst.server.mappers.ProductMapper;
import org.kgromov.apifirst.server.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto createProduct(ProductCreateDto createDto) {
        var savedProduct = productRepository.saveAndFlush(productMapper.dtoToProduct(createDto));
        return productMapper.productToDto(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(UUID productId) {
        return productRepository.findById(productId)
                .map(productMapper::productToDto)
                .orElseThrow();
    }
}

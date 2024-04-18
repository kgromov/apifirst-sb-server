package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.ProductCreateDto;
import org.kgromov.apifirst.model.ProductDto;
import org.kgromov.apifirst.model.ProductUpdateDto;
import org.kgromov.apifirst.server.exceptions.ConflictException;
import org.kgromov.apifirst.server.exceptions.ResourceNotFoundException;
import org.kgromov.apifirst.server.mappers.ProductMapper;
import org.kgromov.apifirst.server.repositories.OrderRepository;
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
    private final OrderRepository orderRepository;

    @Transactional
    public ProductDto updateProduct(UUID productId, ProductUpdateDto product) {
        var productToUpdate = productRepository.findById(productId).orElseThrow(ResourceNotFoundException::new);
        productMapper.updateProduct(product, productToUpdate);
        var updatedProduct = productRepository.saveAndFlush(productToUpdate);
        return productMapper.productToDto(updatedProduct);
    }

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
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void deleteProduct(UUID productId) {
        productRepository.findById(productId).ifPresentOrElse(product -> {
                    if (orderRepository.existsByOrderLines_Product(product)) {
                        throw new ConflictException("Product is associated with orders");
                    }
                    productRepository.delete(product);
                },
                () -> {
                    throw new ResourceNotFoundException("Product not found");
                });
    }
}

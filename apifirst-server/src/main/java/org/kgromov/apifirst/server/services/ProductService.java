package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.ProductDto;
import org.kgromov.apifirst.server.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto createProduct(ProductDto newProduct) {
//        return productRepository.save(newProduct);
        return null;
    }

    public List<ProductDto> getProducts() {
//        return StreamSupport.stream(productRepository.findAll().spliterator(), false).toList();
        return null;
    }

    public ProductDto getProductById(UUID productId) {
//        return productRepository.findById(productId).orElseThrow();
        return null;
    }
}

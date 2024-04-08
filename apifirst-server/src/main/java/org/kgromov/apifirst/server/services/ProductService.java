package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.Product;
import org.kgromov.apifirst.server.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false).toList();
    }

    public Product getProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow();
    }
}

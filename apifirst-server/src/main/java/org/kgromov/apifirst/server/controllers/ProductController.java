package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.Product;
import org.kgromov.apifirst.server.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.kgromov.apifirst.server.controllers.ProductController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class ProductController {
    static final String BASE_URL = "/v1/products";
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Product product){
        Product savedProduct = productService.createProduct(product);
        var uriComponents = UriComponentsBuilder.fromPath(BASE_URL + "/{productId}")
                .buildAndExpand(savedProduct.getId());
        return ResponseEntity.created(URI.create(uriComponents.getPath())).build();
    }

    @GetMapping
    ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}

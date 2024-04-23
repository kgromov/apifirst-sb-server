package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.ProductCreateDto;
import org.kgromov.apifirst.model.ProductDto;
import org.kgromov.apifirst.model.ProductPatchDto;
import org.kgromov.apifirst.model.ProductUpdateDto;
import org.kgromov.apifirst.server.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Void> createProduct(@RequestBody ProductCreateDto createDto) {
        ProductDto savedProduct = productService.createProduct(createDto);
        var uriComponents = UriComponentsBuilder.fromPath(BASE_URL + "/{productId}")
                .buildAndExpand(savedProduct.getId());
        return ResponseEntity.created(URI.create(uriComponents.getPath())).build();
    }

    @GetMapping
    ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDto> getProductById(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID productId,
                                                    @RequestBody ProductUpdateDto updateDto) {
        ProductDto savedProduct = productService.updateProduct(productId, updateDto);
        return ResponseEntity.ok(savedProduct);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> patchProduct(@PathVariable UUID productId,
                                                   @RequestBody ProductPatchDto patchDto) {
        ProductDto savedProduct = productService.patchProduct(productId, patchDto);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}

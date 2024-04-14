package org.kgromov.apifirst.server.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.model.*;
import org.kgromov.apifirst.server.domain.CategoryCode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.kgromov.apifirst.server.controllers.ProductController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ProductControllerTest extends BaseE2ETest {

    @DisplayName("Test new product creation")
    @Test
    void createProduct() throws Exception {
        var newProduct = ProductCreateDto.builder()
                .description("New ProductDto")
                .cost("5.00")
                .price("8.95")
                .categories(List.of(CategoryCode.ELECTRONICS.name()))
                .images(List.of(ImageDto.builder()
                        .uri(URI.create("http://example.com/image.jpg"))
                        .altText("ImageDto Alt Text")
                        .build()))
                .dimensions(DimensionsDto.builder()
                        .length(10)
                        .width(10)
                        .height(10)
                        .build())
                .build();
        mockMvc.perform(post(ProductController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @DisplayName("Test get all products")
    @Test
    void getProducts() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", greaterThan(0)));
    }

    @DisplayName("Test get existed product by id")
    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{productId}", testProduct.getId())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testProduct.getId().toString()));
    }
}
package org.kgromov.apifirst.server.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.model.*;
import org.kgromov.apifirst.server.domain.Product;
import org.kgromov.apifirst.server.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.kgromov.apifirst.server.controllers.ProductController.BASE_URL;
import static org.kgromov.apifirst.server.domain.CategoryCode.ELECTRONICS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ProductControllerTest extends BaseE2ETest {
    @Autowired
    private ProductMapper productMapper;

    @DisplayName("Test new product creation")
    @Test
    void createProduct() throws Exception {
        var newProduct = ProductCreateDto.builder()
                .description("New ProductDto")
                .cost("5.00")
                .price("8.95")
                .categories(List.of(ELECTRONICS.name()))
                .images(List.of(ImageDto.builder()
                        .uri("http://example.com/image.jpg")
                        .altText("ImageDto Alt Text")
                        .build()))
                .dimensions(DimensionsDto.builder()
                        .length(10)
                        .width(10)
                        .height(10)
                        .build())
                .build();
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct))
                )
                .andExpect(status().isCreated())
                .andExpect(openApi().isValid(openApiUrl))
                .andExpect(header().exists("Location"));
    }

    @DisplayName("Test get all products")
    @Test
    void getProducts() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(openApiUrl))
                .andExpect(jsonPath("$.size()", greaterThan(0)));
    }

    @DisplayName("Test get existed product by id")
    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{productId}", testProduct.getId())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(openApiUrl))
                .andExpect(jsonPath("$.id").value(testProduct.getId().toString()));
    }

    @DisplayName("Test get product not existed by id")
    @Test
    void getProductByIdNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{productId}", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(openApi().isValid(openApiUrl));
    }

    // TODO: test more complex scenario with nested entity update
    @DisplayName("Test update product by id")
    @Transactional
    @Test
    void updateProduct() throws Exception {
        ProductUpdateDto productUpdateDto = productMapper.productToUpdateDto(testProduct);
        productUpdateDto.setDescription("Updated Description");

        mockMvc.perform(put(BASE_URL + "/{productId}", testProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(openApiUrl))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @DisplayName("Test update product not existing by id")
    @Transactional
    @Test
    void updateProductNotFound() throws Exception {
        ProductUpdateDto productUpdateDto = productMapper.productToUpdateDto(testProduct);
        productUpdateDto.setDescription("Updated Description");

        mockMvc.perform(put(BASE_URL + "/{productId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isNotFound())
                .andExpect(openApi().isValid(openApiUrl));
    }

    @DisplayName("Test patch product")
    @Transactional
    @Test
    void patchProduct() throws Exception {
        var productPatchDto = productMapper.productToPatchDto(testProduct);
        productPatchDto.setDescription("Updated Description");

        mockMvc.perform(patch(ProductController.BASE_URL + "/{productId}", testProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productPatchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(openApi().isValid(openApiUrl));
    }

    @DisplayName("Test Patch Product Not Found")
    @Transactional
    @Test
    void patchProductNotFound() throws Exception {
        var productPatchDto = productMapper.productToPatchDto(testProduct);
        productPatchDto.setDescription("Updated Description");

        mockMvc.perform(patch(ProductController.BASE_URL + "/{productId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productPatchDto)))
                .andExpect(status().isNotFound())
                .andExpect(openApi().isValid(openApiUrl));
    }

    @DisplayName("Test delete product")
    @Test
    void deleteProduct() throws Exception {
        ProductCreateDto newProduct = createTestProductCreateDto();
        Product savedProduct = productRepository.save(productMapper.dtoToProduct(newProduct));

        mockMvc.perform(delete(BASE_URL + "/{productId}", savedProduct.getId()))
                .andExpect(status().isNoContent())
                .andExpect(openApi().isValid(openApiUrl));

        assertThat(productRepository.findById(savedProduct.getId())).isEmpty();
    }

    @DisplayName("Test delete product that does not exist by id")
    @Test
    void deleteOrderNotFound() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{productId}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(openApi().isValid(openApiUrl));
    }

    @DisplayName("Test delete product that associated with orders")
    @Test
    void deleteOrderConflictProductWithOrders() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{productId}", testProduct.getId()))
                .andExpect(status().isConflict())
                .andExpect(openApi().isValid(openApiUrl));
    }

    private ProductCreateDto createTestProductCreateDto() {
        return ProductCreateDto.builder()
                .description("New Product")
                .cost("5.00")
                .price("8.95")
                .categories(List.of(ELECTRONICS.name()))
                .images(List.of(ImageDto.builder()
                        .uri("http://example.com/image.jpg")
                        .altText("Image Alt Text")
                        .build()))
                .dimensions(DimensionsDto.builder()
                        .length(10)
                        .width(10)
                        .height(10)
                        .build())
                .build();
    }
}
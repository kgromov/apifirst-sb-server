package org.kgromov.apifirst.server.mappers;

import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.model.DimensionsDto;
import org.kgromov.apifirst.model.ImageDto;
import org.kgromov.apifirst.model.ProductCreateDto;
import org.kgromov.apifirst.server.domain.Category;
import org.kgromov.apifirst.server.domain.CategoryCode;
import org.kgromov.apifirst.server.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void dtoToProduct() {
        Category category = categoryRepository.findByCode(CategoryCode.ELECTRONICS.name()).orElseThrow();
        var productCreateDto = buildProductCreateDto(category.getCode());

        var product = productMapper.dtoToProduct(productCreateDto);

        assertNotNull(product);
        assertEquals(productCreateDto.getDescription(), product.getDescription());
        assertEquals(productCreateDto.getCost(), product.getCost());
        assertEquals(productCreateDto.getPrice(), product.getPrice());
        assertEquals(productCreateDto.getDimensions().getHeight(), product.getHeight());
        assertEquals(productCreateDto.getDimensions().getWidth(), product.getWidth());
        assertEquals(productCreateDto.getDimensions().getLength(), product.getLength());
        assertEquals(productCreateDto.getImages().getFirst().getUri(), product.getImages().getFirst().getUri());
        assertEquals(productCreateDto.getImages().getFirst().getAltText(), product.getImages().getFirst().getAltText());
        assertEquals(productCreateDto.getCategories().getFirst(), product.getCategories().getFirst().getCode());

        //test to catch changes, fail test if fields are added
        assertEquals(8, product.getClass().getDeclaredFields().length);
    }

    private ProductCreateDto buildProductCreateDto(String cat) {
        return ProductCreateDto.builder()
                .price("1.0")
                .description("description")
                .images(List.of(ImageDto.builder()
                        .uri("http://example.com/image.jpg")
                        .altText("Image Alt Text")
                        .build()))
                .categories(List.of(cat))
                .cost("1.0")
                .dimensions(DimensionsDto.builder()
                        .height(1)
                        .length(1)
                        .width(1)
                        .build())
                .build();
    }
}
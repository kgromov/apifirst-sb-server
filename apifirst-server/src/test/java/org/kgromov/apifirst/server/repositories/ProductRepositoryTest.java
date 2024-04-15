package org.kgromov.apifirst.server.repositories;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.server.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @DisplayName("Test image persisted")
    @Test
    void testImagePersistence() {
        Product product = productRepository.findAll().getFirst();

        assertThat(product).isNotNull();
        assertThat(product.getImages()).isNotEmpty();
    }
}
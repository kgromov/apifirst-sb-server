package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.Category;
import org.kgromov.apifirst.model.Dimensions;
import org.kgromov.apifirst.model.Image;
import org.kgromov.apifirst.model.Product;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl extends AbstractMapRepository<Product, UUID> implements ProductRepository {
    
    @Override
    public <S extends Product> S save(S entity) {
        Product.ProductBuilder builder = Product.builder();

        builder.id(UUID.randomUUID())
                .description(entity.getDescription())
                .cost(entity.getCost())
                .price(entity.getPrice())
                .created(OffsetDateTime.now())
                .modified(OffsetDateTime.now());

        if (entity.getCategories() != null) {
            builder.categories(entity.getCategories().stream()
                    .map(category -> {
                        return Category.builder()
                                .id(UUID.randomUUID())
                                .name(category.getName())
                                .description(category.getDescription())
                                .created(OffsetDateTime.now())
                                .modified(OffsetDateTime.now())
                                .build();
                    })
                    .collect(Collectors.toList()));
        }

        if (entity.getImages() != null) {
            builder.images(entity.getImages().stream()
                    .map(image -> {
                        return Image.builder()
                                .id(UUID.randomUUID())
                                .uri(image.getUri())
                                .altText(image.getAltText())
                                .created(OffsetDateTime.now())
                                .modified(OffsetDateTime.now())
                                .build();
                    })
                    .collect(Collectors.toList()));
        }

        if (entity.getDimensions() != null) {
            builder.dimensions(Dimensions.builder()
                    .length(entity.getDimensions().getLength())
                    .width(entity.getDimensions().getWidth())
                    .height(entity.getDimensions().getHeight())
                    .build());
        }
        Product product = builder.build();
        entityMap.put(product.getId(), product);
        return (S) product;
    }

    @Override
    public void delete(Product entity) {
        this.entityMap.remove(entity.getId());
    }
}

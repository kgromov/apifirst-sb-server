package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.CategoryDto;
import org.kgromov.apifirst.model.DimensionsDto;
import org.kgromov.apifirst.model.ImageDto;
import org.kgromov.apifirst.model.ProductDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile("in-map")
@Repository
public class ProductRepositoryImpl extends AbstractMapRepository<ProductDto, UUID> implements ProductRepository {
    
    @Override
    public <S extends ProductDto> S save(S entity) {
        var builder = ProductDto.builder();
        builder.id(UUID.randomUUID())
                .description(entity.getDescription())
                .cost(entity.getCost())
                .price(entity.getPrice())
                .created(OffsetDateTime.now())
                .modified(OffsetDateTime.now());

        if (entity.getCategories() != null) {
            builder.categories(entity.getCategories().stream()
                    .map(category -> {
                        return CategoryDto.builder()
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
                        return ImageDto.builder()
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
            builder.dimensions(DimensionsDto.builder()
                    .length(entity.getDimensions().getLength())
                    .width(entity.getDimensions().getWidth())
                    .height(entity.getDimensions().getHeight())
                    .build());
        }
        ProductDto product = builder.build();
        entityMap.put(product.getId(), product);
        return (S) product;
    }

    @Override
    public void delete(ProductDto entity) {
        this.entityMap.remove(entity.getId());
    }
}

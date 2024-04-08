package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.Product;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public class ProductRepositoryImpl extends AbstractMapRepository<Product, UUID> {

    @Override
    public <S extends Product> S save(S entity) {
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .created(OffsetDateTime.now())
                .modified(OffsetDateTime.now())
                .build();
        this.entityMap.put(product.getId(), entity);
        return (S) product;
    }

    @Override
    public void delete(Product entity) {
        this.entityMap.remove(entity.getId());
    }
}

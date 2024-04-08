package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}

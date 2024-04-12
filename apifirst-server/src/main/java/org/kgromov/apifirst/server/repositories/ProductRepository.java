package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.server.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}

package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.server.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByCode(String code);

    List<Category> findAllByCodeIn(Collection<String> codes);
}

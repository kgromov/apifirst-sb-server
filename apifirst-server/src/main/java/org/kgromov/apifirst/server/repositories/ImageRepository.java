package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.server.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}

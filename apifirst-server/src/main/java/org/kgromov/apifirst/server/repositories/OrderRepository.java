package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.server.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}

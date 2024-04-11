package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.OrderDto;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<OrderDto, UUID> {
}

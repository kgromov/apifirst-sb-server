package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.CustomerDto;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<CustomerDto, UUID> {
}

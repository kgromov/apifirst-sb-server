package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.server.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}

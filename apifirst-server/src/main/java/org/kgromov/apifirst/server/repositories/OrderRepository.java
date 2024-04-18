package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.server.domain.Customer;
import org.kgromov.apifirst.server.domain.Order;
import org.kgromov.apifirst.server.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByCustomer(Customer customer);

    boolean existsByOrderLines_Product(Product product);

    @Query("select count(o) > 1 from Order o " +
            "join o.orderLines ol " +
            "where ol.product = :product")
    boolean existsByProduct(Product product);
}

package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.Order;
import org.kgromov.apifirst.model.OrderLine;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl extends AbstractMapRepository<Order, UUID> implements OrderRepository {
    @Override
    public <S extends Order> S save(S entity) {
        Order.OrderBuilder builder = Order.builder();

        builder.id(UUID.randomUUID())
                .orderStatus(entity.getOrderStatus())
                .shipmentInfo(entity.getShipmentInfo())
                .created(OffsetDateTime.now())
                .modified(OffsetDateTime.now());

        if (entity.getCustomer() != null) {
            builder.customer(entity.getCustomer());
        }

        if (entity.getOrderLines() != null){
            builder.orderLines(entity.getOrderLines().stream()
                    .map(orderLine -> {
                        return OrderLine.builder()
                                .id(UUID.randomUUID())
                                .product(orderLine.getProduct()) //might cause NPE
                                .orderQuantity(orderLine.getOrderQuantity())
                                .shipQuantity(orderLine.getShipQuantity())
                                .created(OffsetDateTime.now())
                                .modified(OffsetDateTime.now())
                                .build();
                    })
                    .collect(Collectors.toList()));
        }

        Order savedEntity = builder.build();
        entityMap.put(savedEntity.getId(), savedEntity);
        return (S) savedEntity;
    }

    @Override
    public void delete(Order entity) {
        this.entityMap.remove(entity.getId());
    }
}

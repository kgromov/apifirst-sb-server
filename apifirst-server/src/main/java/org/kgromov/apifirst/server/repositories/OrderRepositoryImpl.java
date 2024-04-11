package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.OrderDto;
import org.kgromov.apifirst.model.OrderLineDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile("in-map")
@Repository
public class OrderRepositoryImpl extends AbstractMapRepository<OrderDto, UUID> implements OrderRepository {
    @Override
    public <S extends OrderDto> S save(S entity) {
       var builder = OrderDto.builder();
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
                        return OrderLineDto.builder()
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

        OrderDto savedEntity = builder.build();
        entityMap.put(savedEntity.getId(), savedEntity);
        return (S) savedEntity;
    }

    @Override
    public void delete(OrderDto entity) {
        this.entityMap.remove(entity.getId());
    }
}

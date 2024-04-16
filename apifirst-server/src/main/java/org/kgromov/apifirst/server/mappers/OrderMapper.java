package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.OrderCreateDto;
import org.kgromov.apifirst.model.OrderDto;
import org.kgromov.apifirst.model.OrderUpdateDto;
import org.kgromov.apifirst.server.domain.Order;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
@DecoratedWith(OrderMapperDecorator.class)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "shipmentInfo", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "selectedPaymentMethod", ignore = true)
    @Mapping(target = "orderLines", ignore = true)
//    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    void updateOrder(OrderUpdateDto orderDto, @MappingTarget Order order);

    @Mapping(target = "selectPaymentMethodId", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    OrderUpdateDto orderToUpdateDto(Order order);


    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "shipmentInfo", ignore = true)
    @Mapping(target = "selectedPaymentMethod", ignore = true)
    Order dtoToOrder(OrderCreateDto createDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "selectedPaymentMethod", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    Order dtoToOrder(OrderDto orderDto);

    @Mapping(source = "timestampAudited.created", target = "created")
    @Mapping(source = "timestampAudited.modified", target = "modified")
    OrderDto orderToDto(Order order);
}
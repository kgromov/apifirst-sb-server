package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.OrderCreateDto;
import org.kgromov.apifirst.model.OrderDto;
import org.kgromov.apifirst.model.OrderUpdateDto;
import org.kgromov.apifirst.server.domain.*;
import org.kgromov.apifirst.server.exceptions.ResourceNotFoundException;
import org.kgromov.apifirst.server.repositories.CustomerRepository;
import org.kgromov.apifirst.server.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;

public abstract class OrderMapperDecorator implements OrderMapper {

    @Qualifier("delegate")
    @Autowired
    private OrderMapper delegate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    @Override
    public void updateOrder(OrderUpdateDto orderDto, Order order) {
        delegate.updateOrder(orderDto, order);
        Customer orderCustomer = customerRepository.findById(orderDto.getCustomerId()).orElseThrow(ResourceNotFoundException::new);
        order.setCustomer(orderCustomer);

        var selectedPaymentMethod = order.getCustomer().getPaymentMethods().stream()
                .filter(pm -> pm.getId().equals(orderDto.getSelectPaymentMethodId()))
                .findFirst()
                .orElseThrow();
        order.setSelectedPaymentMethod(selectedPaymentMethod);

        if (orderDto.getOrderLines() != null) {
            orderDto.getOrderLines().forEach(orderLineDto -> {
                OrderLine existingOrderLine = order.getOrderLines().stream()
                        .filter(ol -> ol.getId().equals(orderLineDto.getId()))
                        .findFirst()
                        .orElseThrow();
                Product product = productRepository.findById(orderLineDto.getProductId()).orElseThrow();
                existingOrderLine.setProduct(product);
                existingOrderLine.setOrderQuantity(orderLineDto.getOrderQuantity());
            });
        }
    }

    @Override
    public OrderUpdateDto orderToUpdateDto(Order order) {
        OrderUpdateDto orderUpdateDto = delegate.orderToUpdateDto(order);
        orderUpdateDto.setCustomerId(order.getCustomer().getId());
        orderUpdateDto.setSelectPaymentMethodId(order.getSelectedPaymentMethod().getId());

        orderUpdateDto.getOrderLines().forEach(orderLineDto -> {
            OrderLine orderLine = order.getOrderLines().stream()
                    .filter(ol -> ol.getId().equals(orderLineDto.getId()))
                    .findFirst()
                    .orElseThrow();
            orderLineDto.setProductId(orderLine.getProduct().getId());
        });
        return orderUpdateDto;
    }


    @Override
    public Order dtoToOrder(OrderCreateDto orderCreate) {
        Customer orderCustomer = customerRepository.findById(orderCreate.getCustomerId()).orElseThrow();
        var selectedPaymentMethod = orderCustomer.getPaymentMethods().stream()
                .filter(pm -> pm.getId().equals(orderCreate.getSelectPaymentMethodId()))
                .findFirst()
                .orElseThrow();
        var orderBuilder = Order.builder()
                .customer(orderCustomer)
                .selectedPaymentMethod(selectedPaymentMethod)
                .orderStatus(OrderStatus.NEW);
        var orderLines = new ArrayList<OrderLine>();
        orderCreate.getOrderLines()
                .forEach(orderLineCreate -> {
                    Product product = productRepository.findById(orderLineCreate.getProductId()).orElseThrow();
                    orderLines.add(OrderLine.builder()
                            .product(product)
                            .orderQuantity(orderLineCreate.getOrderQuantity())
                            .build());
                });
        return orderBuilder.orderLines(orderLines).build();
    }

    @Override
    public Order dtoToOrder(OrderDto orderDto) {
        return delegate.dtoToOrder(orderDto);
    }

    @Override
    public OrderDto orderToDto(Order order) {
        OrderDto orderDto = delegate.orderToDto(order);
        orderDto.getCustomer()
                .selectedPaymentMethod(paymentMethodMapper.paymentMethodToDto(order.getSelectedPaymentMethod()));

        return orderDto;
    }
}













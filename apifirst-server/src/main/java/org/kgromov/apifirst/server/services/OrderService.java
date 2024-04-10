package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.*;
import org.kgromov.apifirst.server.repositories.CustomerRepository;
import org.kgromov.apifirst.server.repositories.OrderRepository;
import org.kgromov.apifirst.server.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public Order saveNewOrder(OrderCreate orderCreate) {
        Customer orderCustomer = customerRepository.findById(orderCreate.getCustomerId()).orElseThrow();
        var builder = Order.builder()
                .customer(OrderCustomer.builder()
                        .id(orderCustomer.getId())
                        .name(orderCustomer.getName())
                        .billToAddress(orderCustomer.getBillToAddress())
                        .shipToAddress(orderCustomer.getShipToAddress())
                        .phone(orderCustomer.getPhone())
                        .selectedPaymentMethod(orderCustomer.getPaymentMethods().stream()
                                .filter(paymentMethod -> paymentMethod.getId()
                                        .equals(orderCreate.getSelectPaymentMethodId()))
                                .findFirst().orElseThrow())
                        .build())
                .orderStatus(Order.OrderStatusEnum.NEW);

        List<OrderLine> orderLines = new ArrayList<>();
        orderCreate.getOrderLines()
                .forEach(orderLineCreate -> {
                    Product product = productRepository.findById(orderLineCreate.getProductId()).orElseThrow();
                    orderLines.add(OrderLine.builder()
                            .product(OrderProduct.builder()
                                    .id(product.getId())
                                    .description(product.getDescription())
                                    .price(product.getPrice())
                                    .build())
                            .orderQuantity(orderLineCreate.getOrderQuantity())
                            .build());
                });

        return orderRepository.save(builder.orderLines(orderLines).build());
    }

    public List<Order> getOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false).toList();
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }
}

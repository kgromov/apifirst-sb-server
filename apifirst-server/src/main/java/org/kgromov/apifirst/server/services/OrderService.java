package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.Order;
import org.kgromov.apifirst.server.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false).toList();
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }
}

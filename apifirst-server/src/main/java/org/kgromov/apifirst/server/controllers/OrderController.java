package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.Order;
import org.kgromov.apifirst.server.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}

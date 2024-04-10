package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.Order;
import org.kgromov.apifirst.model.OrderCreate;
import org.kgromov.apifirst.server.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.kgromov.apifirst.server.controllers.OrderController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class OrderController {
    static final String BASE_URL = "/v1/orders";
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> saveNewOrder(@RequestBody OrderCreate orderCreate){
        Order savedOrder = orderService.saveNewOrder(orderCreate);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + savedOrder.getId())).build();
    }

    @GetMapping
    ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}

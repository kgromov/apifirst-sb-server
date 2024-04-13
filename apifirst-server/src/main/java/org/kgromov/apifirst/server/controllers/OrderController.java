package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.OrderDto;
import org.kgromov.apifirst.model.OrderCreateDto;
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
    public ResponseEntity<Void> createOrder(@RequestBody OrderCreateDto orderCreate){
        OrderDto savedOrder = orderService.createOrder(orderCreate);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + savedOrder.getId())).build();
    }

    @GetMapping
    ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}

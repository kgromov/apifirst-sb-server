package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.OrderCreateDto;
import org.kgromov.apifirst.model.OrderDto;
import org.kgromov.apifirst.model.OrderUpdateDto;
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
    public ResponseEntity<Void> createOrder(@RequestBody OrderCreateDto orderCreate) {
        OrderDto savedOrder = orderService.createOrder(orderCreate);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + savedOrder.getId())).build();
    }

    @GetMapping
    ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{orderId}")
    ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PutMapping("/{orderId}")
    ResponseEntity<OrderDto> updateOrder(@PathVariable("orderId") UUID orderId,
                                         @RequestBody OrderUpdateDto orderUpdateDto) {
        OrderDto savedOrder = orderService.updateOrder(orderId, orderUpdateDto);
        return ResponseEntity.ok(savedOrder);
    }

    @DeleteMapping("/{orderId}")
    ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}

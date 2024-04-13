package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.OrderCreateDto;
import org.kgromov.apifirst.model.OrderDto;
import org.kgromov.apifirst.server.domain.Order;
import org.kgromov.apifirst.server.mappers.OrderMapper;
import org.kgromov.apifirst.server.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDto createOrder(OrderCreateDto orderCreateDto) {
        Order savedOrder = orderRepository.saveAndFlush(orderMapper.dtoToOrder(orderCreateDto));
        return orderMapper.orderToDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::orderToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::orderToDto)
                .orElseThrow();
    }
}

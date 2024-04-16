package org.kgromov.apifirst.server.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.model.OrderCreateDto;
import org.kgromov.apifirst.model.OrderDto;
import org.kgromov.apifirst.model.OrderLineCreateDto;
import org.kgromov.apifirst.model.OrderUpdateDto;
import org.kgromov.apifirst.server.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.kgromov.apifirst.server.controllers.OrderController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class OrderControllerTest extends BaseE2ETest {
    @Autowired private OrderMapper orderMapper;

    @DisplayName("Test create new customer")
    @Test
    @Transactional(readOnly = true)
    void createOrder() throws Exception {
        OrderCreateDto orderCreate = OrderCreateDto.builder()
                .customerId(testCustomer.getId())
                .selectPaymentMethodId(testCustomer.getPaymentMethods().getFirst().getId())
                .orderLines(List.of(OrderLineCreateDto.builder()
                        .productId(testProduct.getId())
                        .orderQuantity(1)
                        .build()))
                .build();
        mockMvc.perform(post(OrderController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreate)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }


    @DisplayName("Test get all products")
    @Test
    void getOrders() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", greaterThan(0)));
    }

    @DisplayName("Test get existed product by id")
    @Test
    void getOrderById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{orderId}", testOrder.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrder.getId().toString()));
    }

    @DisplayName("Test update order")
    @Test
    @Transactional
    void updateOrder() throws Exception {
        testOrder.getOrderLines().getFirst().setOrderQuantity(222);
        OrderUpdateDto orderUpdate = orderMapper.orderToUpdateDto(testOrder);

        ResultActions perform = mockMvc.perform(put(BASE_URL + "/{orderId}", testOrder.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderUpdate))
                .accept(MediaType.APPLICATION_JSON));
        OrderDto updatedOrder = objectMapper.reader().readValue(perform.andReturn().getResponse().getContentAsString(), OrderDto.class);
        assertThat(updatedOrder.getId()).isEqualTo(testOrder.getId());
        assertThat(updatedOrder.getOrderLines().getFirst().getOrderQuantity()).isEqualTo(222);
     /*   perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrder.getId().toString()));
                .andExpect(jsonPath("$.orderLines[0].orderQuantity").value(222));*/
    }
}
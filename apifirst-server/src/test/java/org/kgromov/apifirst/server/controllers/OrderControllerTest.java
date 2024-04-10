package org.kgromov.apifirst.server.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.model.OrderCreate;
import org.kgromov.apifirst.model.OrderLineCreate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.kgromov.apifirst.server.controllers.OrderController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class OrderControllerTest extends BaseE2ETest {

    @DisplayName("Test create new customer")
    @Test
    void createOrder() throws Exception {
        OrderCreate orderCreate = OrderCreate.builder()
                .customerId(testCustomer.getId())
                .selectPaymentMethodId(testCustomer.getPaymentMethods().getFirst().getId())
                .orderLines(List.of(OrderLineCreate.builder()
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
}
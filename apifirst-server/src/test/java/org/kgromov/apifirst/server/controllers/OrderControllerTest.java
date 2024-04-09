package org.kgromov.apifirst.server.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.model.Order;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.greaterThan;
import static org.kgromov.apifirst.server.controllers.OrderController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerTest extends BaseE2ETest {
    private Order testOrder;

    @BeforeEach
    void setUp() {
        super.setUp();
        this.testOrder = orderRepository.findAll().iterator().next();
        // workaround from https://bitbucket.org/atlassian/swagger-request-validator/issues/406/path-params-dont-work-with-openapi-version
        System.setProperty("bind-type", "true");
    }

    @DisplayName("Test get all products")
    @Test
    void getCustomers() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", greaterThan(0)));
    }

    @DisplayName("Test get existed product by id")
    @Test
    void getCustomerById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{orderId}", testOrder.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrder.getId().toString()));
    }
}
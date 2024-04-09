package org.kgromov.apifirst.server.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.model.Customer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
class CustomerControllerTest extends BaseE2ETest {
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        super.setUp();
        this.testCustomer = customerRepository.findAll().iterator().next();
        // workaround from https://bitbucket.org/atlassian/swagger-request-validator/issues/406/path-params-dont-work-with-openapi-version
        System.setProperty("bind-type", "true");
    }

    @DisplayName("Test get all customers")
    @Test
    void getCustomers() throws Exception {
        mockMvc.perform(get(CustomerController.BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", greaterThan(0)));
    }

    @DisplayName("Test get existed customer by id")
    @Test
    void getCustomerById() throws Exception {
        mockMvc.perform(get(CustomerController.BASE_URL + "/{customerId}", testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testCustomer.getId().toString()));
    }
}
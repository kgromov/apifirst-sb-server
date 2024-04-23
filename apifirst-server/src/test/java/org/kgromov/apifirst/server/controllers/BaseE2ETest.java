package org.kgromov.apifirst.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.kgromov.apifirst.server.domain.Customer;
import org.kgromov.apifirst.server.domain.Order;
import org.kgromov.apifirst.server.domain.Product;
import org.kgromov.apifirst.server.repositories.CustomerRepository;
import org.kgromov.apifirst.server.repositories.OrderRepository;
import org.kgromov.apifirst.server.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.servlet.LogbookFilter;

// TODO: fix to use test containers; use @WebMvcTest for controllers and layers for others tests
public abstract class BaseE2ETest {
    @Autowired protected CustomerRepository customerRepository;
    @Autowired protected ProductRepository productRepository;
    @Autowired protected OrderRepository orderRepository;
    @Autowired protected WebApplicationContext wac;
    // seems new ObjectMapper().findAndRegisterModules(); is default implementation
    @Autowired protected ObjectMapper objectMapper;
    @Value("${openapi.docs.uri}")
    protected String openApiUrl;
    public MockMvc mockMvc;

    protected Customer testCustomer;
    protected Product testProduct;
    protected Order testOrder;

    @BeforeEach
    void setUp() {
        // workaround from https://bitbucket.org/atlassian/swagger-request-validator/issues/406/path-params-dont-work-with-openapi-version
        System.setProperty("bind-type", "true");
        testCustomer = customerRepository.findAll().getFirst();
        testProduct = productRepository.findAll().getFirst();
        testOrder = orderRepository.findAll().getFirst();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new LogbookFilter(Logbook.create()))
                .build();
    }
}

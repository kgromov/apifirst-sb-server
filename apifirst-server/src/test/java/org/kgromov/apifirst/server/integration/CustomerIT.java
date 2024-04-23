package org.kgromov.apifirst.server.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.ApiClient;
import org.kgromov.apifirst.client.CustomerApi;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerIT {
    private CustomerApi customerApi;

    @BeforeEach
    void setUp() {
        ApiClient apiClient = new ApiClient();
//        ApiClient apiClient = new ApiClient(new RestTemplate());
        apiClient.setBasePath("http://localhost:8080");
        this.customerApi = new CustomerApi(apiClient);
    }

    @DisplayName("Test get customers")
    @Test
    void listCustomers() {
        var customers = customerApi.getCustomers();
        assertThat(customers).isNotEmpty();
    }
}

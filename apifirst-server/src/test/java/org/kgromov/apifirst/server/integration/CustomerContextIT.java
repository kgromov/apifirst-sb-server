package org.kgromov.apifirst.server.integration;

// Does not work for some reason even with explicit component scan - probably test configuration is required
//@Disabled
//@SpringBootTest
//@ComponentScan(basePackages = {"org.kgromov.apifirst.client"})
//public class CustomerContextIT {
//    @Autowired private CustomerApi customerApi;
//
//    @DisplayName("Test get customers")
//    @Test
//    void listCustomers() {
//        var customers = customerApi.getCustomers();
//        assertThat(customers).isNotEmpty();
//    }
//}

package org.kgromov.apifirst.server.bootstrap;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kgromov.apifirst.server.domain.*;
import org.kgromov.apifirst.server.repositories.CategoryRepository;
import org.kgromov.apifirst.server.repositories.CustomerRepository;
import org.kgromov.apifirst.server.repositories.OrderRepository;
import org.kgromov.apifirst.server.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toSet;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        this.populateCategories();
        this.populateCustomersData();
        this.populateProductsData();
        this.populateOrders();
    }

    private void populateCategories() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("populateCategories");
        var categories = Arrays.stream(CategoryCode.values())
                .map(code -> Category.builder()
                        .code(code.name())
                        .name(code.getName())
                        .description(code.getName())
                        .build()
                )
                .collect(toSet());
        categoryRepository.saveAll(categories);
        stopWatchAndLog(stopWatch);
    }

    private void populateCustomersData() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("populateCustomerData");
        Address address1 = Address.builder()
                .addressLine1("1234 W Some Street")
                .city("Some City")
                .state("FL")
                .zip("33701")
                .build();

        Customer customer1 = Customer.builder()
                .name(Name.builder()
                        .firstName("John")
                        .lastName("Thompson")
                        .build())
                .billToAddress(address1)
                .shipToAddress(address1)
                .email("john@springframework.guru")
                .phone("800-555-1212")
                .paymentMethods(List.of(PaymentMethod.builder()
                        .displayName("My Card")
                        .cardNumber(12341234)
                        .expiryMonth(12)
                        .expiryYear(26)
                        .cvv(123)
                        .build()))
                .build();

        Address address2 = Address.builder()
                .addressLine1("1234 W Some Street")
                .city("Some City")
                .state("FL")
                .zip("33701")
                .build();

        Customer customer2 = Customer.builder()
                .name(Name.builder()
                        .firstName("Jim")
                        .lastName("Smith")
                        .build())
                .billToAddress(address2)
                .shipToAddress(address2)
                .email("jim@springframework.guru")
                .phone("800-555-1212")
                .paymentMethods(List.of(PaymentMethod.builder()
                        .displayName("My Other Card")
                        .cardNumber(1234888)
                        .expiryMonth(12)
                        .expiryYear(26)
                        .cvv(456)
                        .build()))
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        stopWatchAndLog(stopWatch);
    }

    private void populateProductsData() {
        var categories = categoryRepository.findAll();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("populateProductData");
        Product product1 = Product.builder()
                .description("Product 1")
                .categories(List.of(categories.getFirst()))
                .cost("12.99")
                .price("14.99")
                .dimensions(Dimensions.builder()
                        .height(1)
                        .length(2)
                        .width(3)
                        .build())
                .images(List.of(Image.builder()
                        .uri(URI.create("http://example.com/image1"))
                        .altText("Image 1")
                        .build()))
                .build();

        Product product2 = Product.builder()
                .description("Product 2")
                .categories(List.of(categories.getLast()))
                .cost("12.99")
                .price("14.99")
                .dimensions(Dimensions.builder()
                        .height(1)
                        .length(2)
                        .width(3)
                        .build())
                .images(List.of(Image.builder()
                        .uri(URI.create("http://example.com/image2"))
                        .altText("Image 2")
                        .build()))
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        stopWatchAndLog(stopWatch);
    }

    private void populateOrders() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("populateOrders");

        List<Customer> customers = customerRepository.findAll();
        Customer savedCustomer1 = customers.get(0);
        Customer savedCustomer2 = customers.get(1);

        List<Product> products = productRepository.findAll();
        Product savedProduct1 = products.get(0);
        Product savedProduct2 = products.get(1);

        Order order1 = Order.builder()
                .customer(savedCustomer1)
                .orderStatus(OrderStatus.NEW)
                .shipmentInfo("shipment info")
                .orderLines(List.of(OrderLine.builder()
                                .product(savedProduct1)
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build(),
                        OrderLine.builder()
                                .product(savedProduct1)
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build()))
                .build();

        Order order2 = Order.builder()
                .customer(savedCustomer2)
                .orderStatus(OrderStatus.NEW)
                .shipmentInfo("shipment info #2")
                .orderLines(List.of(OrderLine.builder()
                                .product(savedProduct2)
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build(),
                        OrderLine.builder()
                                .product(savedProduct2)
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build()))
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);
        stopWatchAndLog(stopWatch);
    }

    private void stopWatchAndLog(StopWatch stopWatch) {
        stopWatch.stop();
        var taskInfo = stopWatch.lastTaskInfo();
        log.info("Populate {} in {} ms", taskInfo.getTaskName(), taskInfo.getTime(TimeUnit.MILLISECONDS));
    }
}

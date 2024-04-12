package org.kgromov.apifirst.server.bootstrap;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kgromov.apifirst.model.*;
import org.kgromov.apifirst.server.repositories.CustomerRepository;
import org.kgromov.apifirst.server.repositories.OrderRepository;
import org.kgromov.apifirst.server.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        this.populateCustomersData();
        this.populateProductsData();
        this.populateOrders();
    }

    private void populateCustomersData() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("populateCustomerData");
        AddressDto address1 = AddressDto.builder()
                .addressLine1("1234 W Some Street")
                .city("Some City")
                .state("FL")
                .zip("33701")
                .build();

        CustomerDto customer1 = CustomerDto.builder()
                .name(NameDto.builder()
                        .firstName("John")
                        .lastName("Thompson")
                        .build())
                .billToAddress(address1)
                .shipToAddress(address1)
                .email("john@springframework.guru")
                .phone("800-555-1212")
                .paymentMethods(List.of(PaymentMethodDto.builder()
                        .displayName("My Card")
                        .cardNumber(12341234)
                        .expiryMonth(12)
                        .expiryYear(26)
                        .cvv(123)
                        .build()))
                .build();

        AddressDto address2 = AddressDto.builder()
                .addressLine1("1234 W Some Street")
                .city("Some City")
                .state("FL")
                .zip("33701")
                .build();

        CustomerDto customer2 = CustomerDto.builder()
                .name(NameDto.builder()
                        .firstName("Jim")
                        .lastName("Smith")
                        .build())
                .billToAddress(address2)
                .shipToAddress(address2)
                .email("jim@springframework.guru")
                .phone("800-555-1212")
                .paymentMethods(List.of(PaymentMethodDto.builder()
                        .displayName("My Other Card")
                        .cardNumber(1234888)
                        .expiryMonth(12)
                        .expiryYear(26)
                        .cvv(456)
                        .build()))
                .build();

     /*   customerRepository.save(customer1);
        customerRepository.save(customer2);*/
        stopWatch.stop();
        var taskInfo = stopWatch.lastTaskInfo();
        log.info("Populate {} in {} ms", taskInfo.getTaskName(), taskInfo.getTime(TimeUnit.MILLISECONDS));
    }

    private void populateProductsData() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("populateProductData");
        ProductDto product1 = ProductDto.builder()
                .description("ProductDto 1")
                .categories(List.of(CategoryDto.builder()
                        .name("CategoryDto 1")
                        .description("CategoryDto 1 Description")
                        .build()))
                .cost("12.99")
                .price("14.99")
                .dimensions(DimensionsDto.builder()
                        .height(1)
                        .length(2)
                        .width(3)
                        .build())
                .images(List.of(ImageDto.builder()
                        .uri(URI.create("http://example.com/image1"))
                        .altText("ImageDto 1")
                        .build()))
                .build();

        ProductDto product2 = ProductDto.builder()
                .description("ProductDto 2")
                .categories(List.of(CategoryDto.builder()
                        .name("CategoryDto 2")
                        .description("CategoryDto 2 Description")
                        .build()))
                .cost("12.99")
                .price("14.99")
                .dimensions(DimensionsDto.builder()
                        .height(1)
                        .length(2)
                        .width(3)
                        .build())
                .images(List.of(ImageDto.builder()
                        .uri(URI.create("http://example.com/image2"))
                        .altText("ImageDto 2")
                        .build()))
                .build();

      /*  productRepository.save(product1);
        productRepository.save(product2);*/
        stopWatch.stop();
        var taskInfo = stopWatch.lastTaskInfo();
        log.info("Populate {} in {} ms", taskInfo.getTaskName(), taskInfo.getTime(TimeUnit.MILLISECONDS));
    }

    private void populateOrders() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("populateOrders");

        /*List<CustomerDto> customers = StreamSupport.stream(customerRepository.findAll().spliterator(), false).toList();
        CustomerDto savedCustomer1 = customers.get(0);
        CustomerDto savedCustomer2 = customers.get(1);

        List<ProductDto> products = StreamSupport.stream(productRepository.findAll().spliterator(), false).toList();
        ProductDto savedProduct1 = products.get(0);
        ProductDto savedProduct2 = products.get(1);

        OrderDto order1 = OrderDto.builder()
                .customer(OrderCustomerDto.builder()
                        .id(savedCustomer1.getId())
                        .name(savedCustomer1.getName())
                        .billToAddress(savedCustomer1.getBillToAddress())
                        .shipToAddress(savedCustomer1.getShipToAddress())
                        .phone(savedCustomer1.getPhone())
                        .selectedPaymentMethod(savedCustomer1.getPaymentMethods().getFirst())
                        .build())
                .orderStatus(OrderDto.OrderStatusEnum.NEW)
                .shipmentInfo("shipment info")
                .orderLines(List.of(OrderLineDto.builder()
                                .product(OrderProductDto.builder()
                                        .id(savedProduct1.getId())
                                        .description(savedProduct1.getDescription())
                                        .price(savedProduct1.getPrice())
                                        .build())
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build(),
                        OrderLineDto.builder()
                                .product(OrderProductDto.builder()
                                        .id(savedProduct2.getId())
                                        .description(savedProduct2.getDescription())
                                        .price(savedProduct2.getPrice())
                                        .build())
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build()))
                .build();

        OrderDto order2 = OrderDto.builder()
                .customer(OrderCustomerDto.builder()
                        .id(savedCustomer2.getId())
                        .name(savedCustomer1.getName())
                        .billToAddress(savedCustomer2.getBillToAddress())
                        .shipToAddress(savedCustomer2.getShipToAddress())
                        .phone(savedCustomer2.getPhone())
                        .selectedPaymentMethod(savedCustomer2.getPaymentMethods().getFirst())
                        .build())
                .orderStatus(OrderDto.OrderStatusEnum.NEW)
                .shipmentInfo("shipment info #2")
                .orderLines(List.of(OrderLineDto.builder()
                                .product(OrderProductDto.builder()
                                        .id(savedProduct1.getId())
                                        .description(savedProduct1.getDescription())
                                        .price(savedProduct1.getPrice())
                                        .build())
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build(),
                        OrderLineDto.builder()
                                .product(OrderProductDto.builder()
                                        .id(savedProduct2.getId())
                                        .description(savedProduct2.getDescription())
                                        .price(savedProduct2.getPrice())
                                        .build())
                                .orderQuantity(1)
                                .shipQuantity(1)
                                .build()))
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);*/
        stopWatch.stop();
        var taskInfo = stopWatch.lastTaskInfo();
        log.info("Populate {} in {} ms", taskInfo.getTaskName(), taskInfo.getTime(TimeUnit.MILLISECONDS));
    }
}

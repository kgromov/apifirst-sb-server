package org.kgromov.apifirst.server.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.model.AddressDto;
import org.kgromov.apifirst.model.CustomerDto;
import org.kgromov.apifirst.model.NameDto;
import org.kgromov.apifirst.model.PaymentMethodDto;
import org.kgromov.apifirst.server.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CustomerServiceTest {
    @Autowired private CustomerService customerService;
    @Autowired private CustomerRepository customerRepository;

    @DisplayName("Create new customer with payment methods")
    @Transactional
    @Test
    void createCustomer() {
        CustomerDto customerDto = createCustomerDTO();

        CustomerDto savedCustomer = customerService.createCustomer(customerDto);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getCreated()).isNotNull();
        assertThat(savedCustomer.getModified()).isNotNull();

        var customer = customerRepository.findById(savedCustomer.getId()).orElseThrow();

        assertThat(customer.getPaymentMethods()).isNotEmpty();

        var paymentMethod = customer.getPaymentMethods().getFirst();
        assertThat(paymentMethod.getId()).isNotNull();
        assertThat(paymentMethod.getCreated()).isNotNull();
        assertThat(paymentMethod.getModified()).isNotNull();

        assertThat(customer.getFirstName()).isEqualTo(customerDto.getName().getFirstName());
    }

    @Transactional(readOnly = true)
    @Test
    void getCustomers() {
    }

    @Transactional(readOnly = true)
    @Test
    void getCustomerById() {
    }

    private CustomerDto createCustomerDTO() {
        return CustomerDto.builder()
                .name(NameDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .build())
                .billToAddress(AddressDto.builder()
                        .addressLine1("1234 Main Street")
                        .city("San Diego")
                        .state("CA")
                        .zip("92101")
                        .build())
                .shipToAddress(AddressDto.builder()
                        .addressLine1("1234 Main Street")
                        .city("San Diego")
                        .state("CA")
                        .zip("92101")
                        .build())
                .email("joe@example.com")
                .phone("555-555-5555")
                .paymentMethods(List.of(PaymentMethodDto.builder()
                        .displayName("My Card")
                        .cardNumber(1234123412)
                        .expiryMonth(12)
                        .expiryYear(2020)
                        .cvv(123).build()))
                .build();
    }
}
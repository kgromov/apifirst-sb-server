package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.CustomerDto;
import org.springframework.stereotype.Service;
import org.kgromov.apifirst.server.repositories.CustomerRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDto createCustomer(CustomerDto newCustomer) {
        return customerRepository.save(newCustomer);
    }

    public List<CustomerDto> getCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false).toList();
    }

    public CustomerDto getCustomer(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow();
    }
}

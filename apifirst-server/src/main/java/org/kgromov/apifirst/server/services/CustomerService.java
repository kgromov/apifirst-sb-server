package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.Customer;
import org.springframework.stereotype.Service;
import org.kgromov.apifirst.server.repositories.CustomerRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false).toList();
    }

    public Customer getCustomer(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow();
    }
}

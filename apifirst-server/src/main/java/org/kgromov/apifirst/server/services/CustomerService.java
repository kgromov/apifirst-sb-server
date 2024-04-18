package org.kgromov.apifirst.server.services;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.CustomerDto;
import org.kgromov.apifirst.server.exceptions.ResourceNotFoundException;
import org.kgromov.apifirst.server.domain.Customer;
import org.kgromov.apifirst.server.mappers.CustomerMapper;
import org.kgromov.apifirst.server.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer newCustomer = customerRepository.saveAndFlush(customerMapper.dtoToCustomer(customerDto));
        return customerMapper.customerToDto(newCustomer);
    }

    public List<CustomerDto> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToDto)
                .toList();
    }

    public CustomerDto getCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::customerToDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public CustomerDto updateCustomer(UUID customerId, CustomerDto updateDtp) {
        var customerToUpdate = customerRepository.findById(customerId).orElseThrow(ResourceNotFoundException::new);
        customerMapper.updateCustomer(updateDtp, customerToUpdate);
        var updatedCustomer = customerRepository.saveAndFlush(customerToUpdate);
        return customerMapper.customerToDto(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(UUID customerId) {
        customerRepository.findById(customerId).ifPresentOrElse(
                customerRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Customer not found");
                });
    }
}

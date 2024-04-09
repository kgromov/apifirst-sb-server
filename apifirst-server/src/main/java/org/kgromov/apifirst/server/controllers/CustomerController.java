package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.Customer;
import org.kgromov.apifirst.server.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(CustomerController.BASE_URL)
@RequiredArgsConstructor
public class CustomerController {
    public static final String BASE_URL = "/v1/customers";
    private final CustomerService customerService;

    @GetMapping
    ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }
}

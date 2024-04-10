package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.Customer;
import org.kgromov.apifirst.model.Product;
import org.kgromov.apifirst.server.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(CustomerController.BASE_URL)
@RequiredArgsConstructor
public class CustomerController {
    static final String BASE_URL = "/v1/customers";
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Customer customer){
        Customer createdCustomer = customerService.createCustomer(customer);
        var uriComponents = UriComponentsBuilder.fromPath(BASE_URL + "/{customerId}")
                .buildAndExpand(createdCustomer.getId());
        return ResponseEntity.created(URI.create(uriComponents.getPath())).build();
    }

    @GetMapping
    ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }
}

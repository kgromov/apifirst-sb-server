package org.kgromov.apifirst.server.controllers;

import lombok.RequiredArgsConstructor;
import org.kgromov.apifirst.model.CustomerDto;
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
    public ResponseEntity<Void> createProduct(@RequestBody CustomerDto customer){
        CustomerDto createdCustomer = customerService.createCustomer(customer);
        var uriComponents = UriComponentsBuilder.fromPath(BASE_URL + "/{customerId}")
                .buildAndExpand(createdCustomer.getId());
        return ResponseEntity.created(URI.create(uriComponents.getPath())).build();
    }

    @GetMapping
    ResponseEntity<List<CustomerDto>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @PutMapping("/{customerId}")
    ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID customerId,
                                                @RequestBody CustomerDto updateDtp) {
        CustomerDto udpatedCustomer = customerService.updateCustomer(customerId, updateDtp);
        return ResponseEntity.ok(udpatedCustomer);
    }
}

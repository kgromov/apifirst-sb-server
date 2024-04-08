package org.kgromov.apifirst.server.controllees;

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
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    ResponseEntity<Customer> getCustomers(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }
}

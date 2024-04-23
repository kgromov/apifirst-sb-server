package org.kgromov.apifirst.server.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kgromov.apifirst.model.*;
import org.kgromov.apifirst.server.domain.Customer;
import org.kgromov.apifirst.server.mappers.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.kgromov.apifirst.server.controllers.CustomerController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CustomerControllerTest extends BaseE2ETest {
    @Autowired private CustomerMapper customerMapper;

    @DisplayName("Test new customer creation")
    @Test
    void createCustomer() throws Exception {
        CustomerDto customerDto = this.createCustomerDto();
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto))
                )
                .andExpect(status().isCreated())
                .andExpect(openApi().isValid(openApiUrl))
                .andExpect(header().exists("Location"));
    }

    @DisplayName("Test new create customer bad request")
    @Test
    void createCustomerBadRequest() throws Exception {
        CustomerDto customerDto = this.createCustomerDto();
        customerDto.setName(null);
        customerDto.setPhone("abc");
        customerDto.getBillToAddress().setZip("1");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Test get all customers")
    @Test
    void getCustomers() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(openApiUrl))
                .andExpect(jsonPath("$.size()", greaterThan(0)));
    }

    @DisplayName("Test get existed customer by id")
    @Test
    void getCustomerById() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{customerId}", testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(openApiUrl))
                .andExpect(jsonPath("$.id").value(testCustomer.getId().toString()));
    }

    @DisplayName("Test get customer by not existed id")
    @Test
    void getCustomerByIdNotFound() throws Exception {
        mockMvc.perform(get(OrderController.BASE_URL + "/{customerId}", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(openApi().isValid(openApiUrl));
    }

    @DisplayName("Test update customer")
    @Transactional
    @Test
    void updateCustomer() throws Exception{
        CustomerDto customerDto = customerMapper.customerToDto(testCustomer);
        customerDto.setPhone("444-222-333");
        customerDto.setEmail("my@test.com");

        mockMvc.perform(put(BASE_URL + "/{customerId}", testCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(openApi().isValid(openApiUrl))
                .andExpect(jsonPath("$.phone").value(customerDto.getPhone()))
                .andExpect(jsonPath("$.email").value(customerDto.getEmail()));
    }

    @DisplayName("Test update customer that does not exist by id")
    @Transactional
    @Test
    void updateCustomerNotFound() throws Exception{
        CustomerDto customerDto = customerMapper.customerToDto(testCustomer);
        customerDto.setPhone("444-222-333");

        mockMvc.perform(put(BASE_URL + "/{customerId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isNotFound())
                .andExpect(openApi().isValid(openApiUrl));
    }

    @DisplayName("Test delete customer")
    @Test
    void deleteCustomer() throws Exception {
        CustomerDto customer = this.createCustomerDto();
        Customer savedCustomer = customerRepository.saveAndFlush(customerMapper.dtoToCustomer(customer));

        mockMvc.perform(delete(BASE_URL + "/{customerId}", savedCustomer.getId()))
                .andExpect(status().isNoContent())
                .andExpect(openApi().isValid(openApiUrl));

        assertThat(customerRepository.findById(savedCustomer.getId())).isEmpty();
    }

    @Transactional
    @DisplayName("Test Patch Customer")
    @Test
    void testPatchCustomer() throws Exception {
        var customerPatch = this.createCustomerPatchDto(testCustomer);

        mockMvc.perform(patch(BASE_URL + "/{customerId}", testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerPatch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name.firstName").value("Updated"))
                .andExpect(jsonPath("$.paymentMethods[0].displayName").value("NEW NAME"))
                .andExpect(openApi().isValid(openApiUrl));
    }

    @Transactional
    @DisplayName("Test Patch Customer Not Found")
    @Test
    void testPatchCustomerNotFound() throws Exception {
        var customerPatch = this.createCustomerPatchDto(testCustomer);

        mockMvc.perform(patch(BASE_URL + "/{customerId}", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerPatch)))
                .andExpect(status().isNotFound())
                .andExpect(openApi().isValid(openApiUrl));
    }


    @DisplayName("Test delete customer but random UUID")
    @Test
    void deleteCustomerNotFound() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{customerId}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(openApi().isValid(openApiUrl));
    }

    @DisplayName("Test delete customer with payment methods")
    @Test
    void deleteCustomerConflict() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{customerId}", testCustomer.getId()))
                .andExpect(status().isConflict())
                .andExpect(openApi().isValid(openApiUrl));
    }

    @Disabled
    @DisplayName("Test delete customer with orders")
    @Transactional
    @Test
    void deleteCustomerConflictWithOrder() throws Exception {
      /*  CustomerDto customerDto = customerMapper.customerToDto(testCustomer);
        customerDto.setPaymentMethods(emptyList());
        Customer customer = customerMapper.dtoToCustomer(customerDto);*/
        testCustomer.setPaymentMethods(new ArrayList<>());
        Customer savedCustomer = customerRepository.saveAndFlush(testCustomer);

        mockMvc.perform(delete(BASE_URL + "/{customerId}", savedCustomer.getId()))
                .andExpect(status().isConflict())
                .andExpect(openApi().isValid(openApiUrl));
    }

    private CustomerDto createCustomerDto() {
        return CustomerDto.builder()
                .name(NameDto.builder()
                        .lastName("Doe")
                        .firstName("John")
                        .build())
                .phone("555-555-5555")
                .email("john@example.com")
                .shipToAddress(AddressDto.builder()
                        .addressLine1("123 Main St")
                        .city("Denver")
                        .state("CO")
                        .zip("80216")
                        .build())
                .billToAddress(AddressDto.builder()
                        .addressLine1("123 Main St")
                        .city("Denver")
                        .state("CO")
                        .zip("80216")
                        .build())
                .paymentMethods(List.of(PaymentMethodDto.builder()
                        .displayName("My Other Card")
                        .cardNumber(1234888)
                        .expiryMonth(12)
                        .expiryYear(26)
                        .cvv(456)
                        .build()))
                .build();
    }
    private CustomerPatchDto createCustomerPatchDto(Customer customer) {
        return CustomerPatchDto.builder()
                .name(CustomerNamePatchDto.builder()
                        .firstName("Updated")
                        .build())
                .paymentMethods(List.of(CustomerPaymentMethodPatchDto.builder()
                        .id(customer.getPaymentMethods().getFirst().getId())
                        .displayName("NEW NAME")
                        .build()))
                .build();
    }
}

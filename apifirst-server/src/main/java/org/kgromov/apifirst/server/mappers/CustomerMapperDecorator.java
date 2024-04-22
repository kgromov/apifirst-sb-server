package org.kgromov.apifirst.server.mappers;


import org.kgromov.apifirst.model.CustomerDto;
import org.kgromov.apifirst.model.CustomerPatchDto;
import org.kgromov.apifirst.server.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class CustomerMapperDecorator implements CustomerMapper {

    @Qualifier("delegate")
    @Autowired
    private CustomerMapper delegate;
    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    @Override
    public CustomerDto customerToDto(Customer customer) {
        return delegate.customerToDto(customer);
    }

    @Override
    public Customer dtoToCustomer(CustomerDto customerDto) {
        return delegate.dtoToCustomer(customerDto);
    }

    @Override
    public void updateCustomer(CustomerDto customerDto, Customer customer) {
        delegate.updateCustomer(customerDto, customer);
    }

    @Override
    public CustomerPatchDto customerToPatchDto(Customer customer) {
        return delegate.customerToPatchDto(customer);
    }

    @Override
    public void patchCustomer(CustomerPatchDto customerPatchDto, Customer customer) {
        delegate.patchCustomer(customerPatchDto, customer);
        if (customerPatchDto.getPaymentMethods() != null) {
            customerPatchDto.getPaymentMethods().forEach(paymentMethodPatchDto ->
                    customer.getPaymentMethods()
                            .stream()
                            .filter(paymentMethod -> paymentMethod.getId().equals(paymentMethodPatchDto.getId()))
                            .forEach(paymentMethod -> paymentMethodMapper.updatePaymentMethod(paymentMethodPatchDto, paymentMethod)));
        }
    }
}

package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.CustomerDto;
import org.kgromov.apifirst.server.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Customer dtoToCustomer(CustomerDto customerDto);
}

package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.CustomerDto;
import org.kgromov.apifirst.server.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {

    @Mapping(source = "timestampAudited.created", target = "created")
    @Mapping(source = "timestampAudited.modified", target = "modified")
    CustomerDto customerToDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    Customer dtoToCustomer(CustomerDto customerDto);
}

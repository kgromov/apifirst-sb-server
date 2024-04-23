package org.kgromov.apifirst.server.mappers;

import org.kgromov.apifirst.model.CustomerPaymentMethodPatchDto;
import org.kgromov.apifirst.model.PaymentMethodDto;
import org.kgromov.apifirst.server.domain.PaymentMethod;
import org.mapstruct.*;

@Mapper
public interface PaymentMethodMapper {

  /*  @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    PaymentMethod dtoToPaymentMethod(PaymentMethodDto dto);*/

    @Mapping(source = "timestampAudited.created", target = "created")
    @Mapping(source = "timestampAudited.modified", target = "modified")
    PaymentMethodDto paymentMethodToDto(PaymentMethod paymentMethod);

    @Mapping(target = "timestampAudited.created", ignore = true)
    @Mapping(target = "timestampAudited.modified", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updatePaymentMethod(CustomerPaymentMethodPatchDto patchDto, @MappingTarget PaymentMethod paymentMethod);
}

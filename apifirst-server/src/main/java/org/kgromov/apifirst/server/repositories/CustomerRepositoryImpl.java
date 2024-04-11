package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.AddressDto;
import org.kgromov.apifirst.model.CustomerDto;
import org.kgromov.apifirst.model.PaymentMethodDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Profile("in-map")
@Repository
public class CustomerRepositoryImpl extends AbstractMapRepository<CustomerDto, UUID> implements CustomerRepository {

    @Override
    public <S extends CustomerDto> S save(S entity) {
        UUID id = UUID.randomUUID();

        var builder1 = CustomerDto.builder();
        builder1.id(id);

        if (entity.getBillToAddress() != null){
            builder1.billToAddress(AddressDto.builder()
                    .id(UUID.randomUUID())
                    .addressLine1(entity.getBillToAddress().getAddressLine1())
                    .addressLine2(entity.getBillToAddress().getAddressLine2())
                    .city(entity.getBillToAddress().getCity())
                    .state(entity.getBillToAddress().getState())
                    .zip(entity.getBillToAddress().getZip())
                    .created(OffsetDateTime.now())
                    .modified(OffsetDateTime.now())
                    .build());
        }

        if (entity.getShipToAddress() != null) {
            builder1.shipToAddress(AddressDto.builder()
                    .id(UUID.randomUUID())
                    .addressLine1(entity.getShipToAddress().getAddressLine1())
                    .addressLine2(entity.getShipToAddress().getAddressLine2())
                    .city(entity.getShipToAddress().getCity())
                    .state(entity.getShipToAddress().getState())
                    .zip(entity.getShipToAddress().getZip())
                    .created(OffsetDateTime.now())
                    .modified(OffsetDateTime.now())
                    .build());
        }

        if (entity.getPaymentMethods() != null) {
            builder1.paymentMethods(entity.getPaymentMethods()
                    .stream()
                    .map(paymentMethod -> PaymentMethodDto.builder()
                            .id(UUID.randomUUID())
                            .displayName(paymentMethod.getDisplayName())
                            .cardNumber(paymentMethod.getCardNumber())
                            .expiryMonth(paymentMethod.getExpiryMonth())
                            .expiryYear(paymentMethod.getExpiryYear())
                            .cvv(paymentMethod.getCvv())
                            .created(OffsetDateTime.now())
                            .modified(OffsetDateTime.now())
                            .build())
                    .collect(toList()));
        }

        CustomerDto customer = builder1.email(entity.getEmail())
                .name(entity.getName())
                .phone(entity.getPhone())
                .created(OffsetDateTime.now())
                .modified(OffsetDateTime.now())
                .build();

        entityMap.put(id, customer);

        return (S) customer;
    }

    @Override
    public void delete(CustomerDto entity) {
        entityMap.remove(entity.getId());
    }
}

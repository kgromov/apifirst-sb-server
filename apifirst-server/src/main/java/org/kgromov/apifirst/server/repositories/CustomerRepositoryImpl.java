package org.kgromov.apifirst.server.repositories;

import org.kgromov.apifirst.model.Address;
import org.kgromov.apifirst.model.Customer;
import org.kgromov.apifirst.model.PaymentMethod;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Repository
public class CustomerRepositoryImpl<S extends Customer> implements CustomerRepository {

    private final Map<UUID, Customer> entityMap = new ConcurrentHashMap<>();

    @Override
    public <S extends Customer> S save(S entity) {
        UUID id = UUID.randomUUID();

        Customer.CustomerBuilder builder1 = Customer.builder();

        builder1.id(id);

        if (entity.getBillToAddress() != null){
            builder1.billToAddress(Address.builder()
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
            builder1.shipToAddress(Address.builder()
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
                    .map(paymentMethod -> PaymentMethod.builder()
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

        Customer customer = builder1.email(entity.getEmail())
                .name(entity.getName())
                .phone(entity.getPhone())
                .created(OffsetDateTime.now())
                .modified(OffsetDateTime.now())
                .build();

        entityMap.put(id, customer);

        return (S) customer;
    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .collect(toList());
    }

    @Override
    public Optional<Customer> findById(UUID uuid) {
        return Optional.of(entityMap.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return entityMap.get(uuid) != null;
    }

    @Override
    public Iterable<Customer> findAll() {
        return entityMap.values();
    }

    @Override
    public Iterable<Customer> findAllById(Iterable<UUID> uuids) {
        return StreamSupport.stream(uuids.spliterator(), false)
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return entityMap.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        entityMap.remove(uuid);
    }

    @Override
    public void delete(Customer entity) {
        entityMap.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        uuids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        entityMap.clear();
    }
}

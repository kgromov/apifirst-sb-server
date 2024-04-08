package org.kgromov.apifirst.server.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public abstract class AbstractMapRepository<T, ID> implements CrudRepository<T, ID> {
    protected final Map<ID, T> entityMap = new ConcurrentHashMap<>();

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .collect(toList());
        }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(this.entityMap.get(id));
    }

    @Override
    public boolean existsById(ID id) {
        return this.entityMap.containsKey(id);
    }

    @Override
    public Iterable<T> findAll() {
        return this.entityMap.values();
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return StreamSupport.stream(ids.spliterator(), false)
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
    public void deleteById(ID id) {
        entityMap.remove(id);
    }


    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
            entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        entityMap.clear();
    }
}

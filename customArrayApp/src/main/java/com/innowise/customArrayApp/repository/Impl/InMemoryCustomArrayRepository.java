package com.innowise.customArrayApp.repository.Impl;

import com.innowise.customArrayApp.entity.CustomArray;
import com.innowise.customArrayApp.entity.CustomArrayFactory;
import com.innowise.customArrayApp.repository.CustomArrayRepository;
import com.innowise.customArrayApp.repository.specification.CustomArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryCustomArrayRepository implements CustomArrayRepository {

    private static final Logger logger = LogManager.getLogger(CustomArrayFactory.class);

    private static InMemoryCustomArrayRepository instance;
    private final Map<Long, CustomArray> storage = new ConcurrentHashMap<>();
    private long nextId = 1;

    public static InMemoryCustomArrayRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryCustomArrayRepository();
            logger.info("InMemoryArrayRepository was CREATED");
        }
        return instance;
    }

    private InMemoryCustomArrayRepository() {
    }

    public synchronized long generateNextId() {
        return nextId++;
    }

    @Override
    public CustomArray save(CustomArray array) {
        storage.put(array.getId(), array);
        logger.info("Saving Array with ID={}...", array.getId());
        return array;
    }

    @Override
    public Optional<CustomArray> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<CustomArray> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public List<CustomArray> findBySpecification(CustomArraySpecification spec) {
        return storage.values().stream()
                .filter(spec::test)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(CustomArray array) {
        deleteById(array.getId());
        logger.info("Deleting Array with ID={}...", array.getId());
    }

    @Override
    public void deleteById(long id) {
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("Array with id=" + id + " not found");
        }
        storage.remove(id);
        logger.info("Deleting Array with ID={}...", id);
    }

    @Override
    public void clear() {
        storage.clear();
        nextId = 1;
        logger.info("InMemoryArrayRepository was cleared");
    }

    @Override
    public List<CustomArray> findAllSorted(Comparator<CustomArray> comparator) {
        return storage.values().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}

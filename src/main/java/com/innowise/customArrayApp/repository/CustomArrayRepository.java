package com.innowise.customArrayApp.repository;

import com.innowise.customArrayApp.entity.CustomArray;
import com.innowise.customArrayApp.repository.specification.CustomArraySpecification;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface CustomArrayRepository {
    CustomArray save(CustomArray array);

    Optional<CustomArray> findById(long id);

    List<CustomArray> findAll();

    List<CustomArray> findBySpecification(CustomArraySpecification spec);

    void delete(CustomArray array);

    void deleteById(long id);

    void clear();

    List<CustomArray> findAllSorted(Comparator<CustomArray> comparator);

    long generateNextId();
}

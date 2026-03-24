package com.innowise.customArrayApp.factory;

import com.innowise.customArrayApp.entity.CustomArray;
import com.innowise.customArrayApp.exception.CustomArrayException;
import com.innowise.customArrayApp.repository.CustomArrayRepository;
import com.innowise.customArrayApp.repository.Impl.InMemoryCustomArrayRepository;
import com.innowise.customArrayApp.warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultArrayFactoryTest {

    private CustomArrayRepository repository;
    private CustomArrayFactory factory;

    @BeforeEach
    void setUp() {
        InMemoryCustomArrayRepository.getInstance().clear();
        Warehouse.getInstance().clear();
        factory = new com.innowise.customArrayApp.entity.CustomArrayFactory();
        repository = InMemoryCustomArrayRepository.getInstance();
    }

    @Test
    @DisplayName("Создание пустого массива")
    void createEmpty() {
        CustomArray arr = factory.createEmpty(3);
        assertThat(arr.size()).isEqualTo(3);
        assertThat(arr.get(0)).isEqualTo(0);
        assertThat(arr.getId()).isGreaterThan(0);
        assertThat(repository.findById(arr.getId())).hasValue(arr);
    }

    @Test
    @DisplayName("Создание из значений")
    void createFromValues() {
        CustomArray arr = factory.createFromValues(10, 20, 30);
        assertThat(arr.size()).isEqualTo(3);
        assertThat(arr.get(0)).isEqualTo(10);
        assertThat(arr.get(1)).isEqualTo(20);
        assertThat(arr.get(2)).isEqualTo(30);
        assertThat(repository.findById(arr.getId())).hasValue(arr);
    }

    @Test
    @DisplayName("Исключение при создании пустого массива отрицательного размера")
    void createEmptyNegativeSize() {
        assertThatThrownBy(() -> factory.createEmpty(-5))
                .isInstanceOf(CustomArrayException.class);
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Создание из пустого массива")
    void createFromEmptyValues() {
        CustomArray arr = factory.createFromValues();
        assertThat(arr.size()).isEqualTo(0);
        assertThat(repository.findById(arr.getId())).hasValue(arr);
    }
}
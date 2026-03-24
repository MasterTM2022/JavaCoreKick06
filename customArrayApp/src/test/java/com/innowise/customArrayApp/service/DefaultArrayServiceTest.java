package com.innowise.customArrayApp.service;

import com.innowise.customArrayApp.entity.CustomArray;
import com.innowise.customArrayApp.exception.CustomArrayException;
import com.innowise.customArrayApp.factory.CustomArrayFactory;
import com.innowise.customArrayApp.repository.CustomArrayRepository;
import com.innowise.customArrayApp.repository.Impl.InMemoryCustomArrayRepository;
import com.innowise.customArrayApp.service.Impl.DefaultCustomArrayService;
import com.innowise.customArrayApp.warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultArrayServiceTest {

    private CustomArrayRepository repository;
    private CustomArrayFactory factory;
    private CustomArrayService service;


    @BeforeEach
    void setUp() {
        InMemoryCustomArrayRepository.getInstance().clear();
        Warehouse.getInstance().clear();
        repository = InMemoryCustomArrayRepository.getInstance();
        factory = new com.innowise.customArrayApp.entity.CustomArrayFactory();
        service = new DefaultCustomArrayService(factory);
    }

    @Test
    @DisplayName("Поиск максимума")
    void findMax() {
        CustomArray arr = factory.createFromValues(3, 7, 2, 9, 1);
        assertThat(service.findMax(arr)).isEqualTo(9);
    }

    @Test
    @DisplayName("Поиск минимума")
    void findMin() {
        CustomArray arr = factory.createFromValues(3, 7, 2, 9, 1);
        assertThat(service.findMin(arr)).isEqualTo(1);
    }

    @Test
    @DisplayName("Сумма элементов")
    void findTotal() {
        CustomArray arr = factory.createFromValues(1, 2, 3);
        assertThat(service.findTotal(arr)).isEqualTo(6);
    }

    @Test
    @DisplayName("Среднее значение")
    void findAverage() {
        CustomArray arr = factory.createFromValues(1, 2, 3);
        assertThat(service.findAverage(arr)).isEqualTo(2.0);
    }

    @Test
    @DisplayName("Сортировка не мутирует исходный массив")
    void sortDoesNotMutateOriginal() {
        CustomArray original = factory.createFromValues(3, 1, 2);
        int[] before = original.toArray();
        CustomArray sorted = service.sort(original);
        assertThat(original.toArray()).containsExactly(before);
        assertThat(sorted.toArray()).containsExactly(1, 2, 3);
    }

    @Test
    @DisplayName("Проверка наличия элемента")
    void contains() {
        CustomArray arr = factory.createFromValues(5, 2, 8);
        assertThat(service.contains(arr, 2)).isTrue();
        assertThat(service.contains(arr, 99)).isFalse();
    }

    @Test
    @DisplayName("Исключение при поиске максимума в пустом массиве")
    void findMaxEmpty() {
        CustomArray empty = factory.createEmpty(0);
        assertThatThrownBy(() -> service.findMax(empty))
                .isInstanceOf(CustomArrayException.class);
    }

    @Test
    @DisplayName("Сортировка пузырьком")
    void sortBubble() {
        CustomArray arr = factory.createFromValues(3, 1, 2);
        CustomArray sorted = service.sortBubble(arr);
        assertThat(sorted.toArray()).containsExactly(1, 2, 3);
    }

    @Test
    @DisplayName("Сортировка слиянием")
    void sortMerge() {
        CustomArray arr = factory.createFromValues(3, 1, 2);
        CustomArray sorted = service.sortMerge(arr);
        assertThat(sorted.toArray()).containsExactly(1, 2, 3);
    }
}
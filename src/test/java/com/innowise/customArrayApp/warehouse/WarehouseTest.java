package com.innowise.customArrayApp.warehouse;

import com.innowise.customArrayApp.entity.CustomArray;
import com.innowise.customArrayApp.entity.CustomArrayFactory;
import com.innowise.customArrayApp.repository.Impl.InMemoryCustomArrayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseTest {
    private Warehouse warehouse;
    private CustomArrayFactory factory;

    @BeforeEach
    void setUp() {
        InMemoryCustomArrayRepository.getInstance().clear();
        Warehouse.getInstance().clear();
        warehouse = Warehouse.getInstance();
        factory = new CustomArrayFactory();
    }

    @Test
    @DisplayName("Warehouse обновляет статистику при создании массива")
    void updatesStatsOnCreate() {
        CustomArray arr = factory.createFromValues(10, 20, 30);
        CustomArrayStats stats = warehouse.getStats(arr.getId());

        assertThat(stats).isNotNull();
        assertThat(stats.getSum()).isEqualTo(60);
        assertThat(stats.getMin()).isEqualTo(10);
        assertThat(stats.getMax()).isEqualTo(30);
        assertThat(stats.getAverage()).isEqualTo(20.0);
    }

    @Test
    @DisplayName("Warehouse обновляет статистику при изменении элемента")
    void updatesStatsOnModify() {
        CustomArray arr = factory.createFromValues(1, 2);
        assertThat(warehouse.getStats(arr.getId()).getSum()).isEqualTo(3);

        arr.set(0, 10); // меняем 1 → 10

        CustomArrayStats updated = warehouse.getStats(arr.getId());
        assertThat(updated.getSum()).isEqualTo(12);
        assertThat(updated.getMin()).isEqualTo(2);
        assertThat(updated.getMax()).isEqualTo(10);
    }
}

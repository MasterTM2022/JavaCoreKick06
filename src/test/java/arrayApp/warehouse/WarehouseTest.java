package arrayApp.warehouse;

import arrayApp.entity.Array;
import arrayApp.entity.DefaultArrayFactory;
import arrayApp.repository.InMemoryArrayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseTest {
    private Warehouse warehouse;
    private DefaultArrayFactory factory;

    @BeforeEach
    void setUp() {
        InMemoryArrayRepository.getInstance().clear();
        Warehouse.getInstance().clear();
        warehouse = Warehouse.getInstance();
        factory = new DefaultArrayFactory();
    }

    @Test
    @DisplayName("Warehouse обновляет статистику при создании массива")
    void updatesStatsOnCreate() {
        Array arr = factory.createFromValues(10, 20, 30);
        ArrayStats stats = warehouse.getStats(arr.getId());

        assertThat(stats).isNotNull();
        assertThat(stats.getSum()).isEqualTo(60);
        assertThat(stats.getMin()).isEqualTo(10);
        assertThat(stats.getMax()).isEqualTo(30);
        assertThat(stats.getAverage()).isEqualTo(20.0);
    }

    @Test
    @DisplayName("Warehouse обновляет статистику при изменении элемента")
    void updatesStatsOnModify() {
        Array arr = factory.createFromValues(1, 2);
        assertThat(warehouse.getStats(arr.getId()).getSum()).isEqualTo(3);

        arr.set(0, 10); // меняем 1 → 10

        ArrayStats updated = warehouse.getStats(arr.getId());
        assertThat(updated.getSum()).isEqualTo(12);
        assertThat(updated.getMin()).isEqualTo(2);
        assertThat(updated.getMax()).isEqualTo(10);
    }
}

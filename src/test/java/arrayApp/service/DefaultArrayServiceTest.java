package arrayApp.service;

import arrayApp.entity.Array;
import arrayApp.exception.ArrayException;
import arrayApp.factory.ArrayFactory;
import arrayApp.entity.DefaultArrayFactory;
import arrayApp.repository.ArrayRepository;
import arrayApp.repository.InMemoryArrayRepository;
import arrayApp.warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultArrayServiceTest {

    private ArrayRepository repository;
    private ArrayFactory factory;
    private ArrayService service;


    @BeforeEach
    void setUp() {
        InMemoryArrayRepository.getInstance().clear();
        Warehouse.getInstance().clear();
        repository = InMemoryArrayRepository.getInstance();
        factory = new DefaultArrayFactory();
        service = new DefaultArrayService(factory);
    }

    @Test
    @DisplayName("Поиск максимума")
    void findMax() {
        Array arr = factory.createFromValues(3, 7, 2, 9, 1);
        assertThat(service.findMax(arr)).isEqualTo(9);
    }

    @Test
    @DisplayName("Поиск минимума")
    void findMin() {
        Array arr = factory.createFromValues(3, 7, 2, 9, 1);
        assertThat(service.findMin(arr)).isEqualTo(1);
    }

    @Test
    @DisplayName("Сумма элементов")
    void findTotal() {
        Array arr = factory.createFromValues(1, 2, 3);
        assertThat(service.findTotal(arr)).isEqualTo(6);
    }

    @Test
    @DisplayName("Среднее значение")
    void findAverage() {
        Array arr = factory.createFromValues(1, 2, 3);
        assertThat(service.findAverage(arr)).isEqualTo(2.0);
    }

    @Test
    @DisplayName("Сортировка не мутирует исходный массив")
    void sortDoesNotMutateOriginal() {
        Array original = factory.createFromValues(3, 1, 2);
        int[] before = original.toArray();
        Array sorted = service.sort(original);
        assertThat(original.toArray()).containsExactly(before);
        assertThat(sorted.toArray()).containsExactly(1, 2, 3);
    }

    @Test
    @DisplayName("Проверка наличия элемента")
    void contains() {
        Array arr = factory.createFromValues(5, 2, 8);
        assertThat(service.contains(arr, 2)).isTrue();
        assertThat(service.contains(arr, 99)).isFalse();
    }

    @Test
    @DisplayName("Исключение при поиске максимума в пустом массиве")
    void findMaxEmpty() {
        Array empty = factory.createEmpty(0);
        assertThatThrownBy(() -> service.findMax(empty))
                .isInstanceOf(ArrayException.class);
    }

    @Test
    @DisplayName("Сортировка пузырьком")
    void sortBubble() {
        Array arr = factory.createFromValues(3, 1, 2);
        Array sorted = service.sortBubble(arr);
        assertThat(sorted.toArray()).containsExactly(1, 2, 3);
    }

    @Test
    @DisplayName("Сортировка слиянием")
    void sortMerge() {
        Array arr = factory.createFromValues(3, 1, 2);
        Array sorted = service.sortMerge(arr);
        assertThat(sorted.toArray()).containsExactly(1, 2, 3);
    }
}
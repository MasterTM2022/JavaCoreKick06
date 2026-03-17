package arrayApp.factory;

import arrayApp.entity.Array;
import arrayApp.entity.DefaultArrayFactory;
import arrayApp.exception.ArrayException;
import arrayApp.repository.ArrayRepository;
import arrayApp.repository.InMemoryArrayRepository;
import arrayApp.warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultArrayFactoryTest {

    private ArrayRepository repository;
    private ArrayFactory factory;

    @BeforeEach
    void setUp() {
        InMemoryArrayRepository.getInstance().clear();
        Warehouse.getInstance().clear();
        factory = new DefaultArrayFactory();
        repository = InMemoryArrayRepository.getInstance();
    }

    @Test
    @DisplayName("Создание пустого массива")
    void createEmpty() {
        Array arr = factory.createEmpty(3);
        assertThat(arr.size()).isEqualTo(3);
        assertThat(arr.get(0)).isEqualTo(0);
        assertThat(arr.getId()).isGreaterThan(0);
        assertThat(repository.findById(arr.getId())).hasValue(arr);
    }

    @Test
    @DisplayName("Создание из значений")
    void createFromValues() {
        Array arr = factory.createFromValues(10, 20, 30);
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
                .isInstanceOf(ArrayException.class);
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Создание из пустого массива")
    void createFromEmptyValues() {
        Array arr = factory.createFromValues();
        assertThat(arr.size()).isEqualTo(0);
        assertThat(repository.findById(arr.getId())).hasValue(arr);
    }
}
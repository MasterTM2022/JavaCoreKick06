package arrayApp.repository;

import arrayApp.entity.Array;
import arrayApp.entity.DefaultArrayFactory;
import arrayApp.factory.ArrayFactory;
import arrayApp.warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InMemoryArrayRepositoryTest {
    private InMemoryArrayRepository repository;
    private ArrayFactory factory;

    @BeforeEach
    void setUp() {
        InMemoryArrayRepository.getInstance().clear();
        Warehouse.getInstance().clear();
        factory = new DefaultArrayFactory();
        repository = InMemoryArrayRepository.getInstance();
    }

    @Test
    @DisplayName("Массивы автоматически сохраняются при создании через фабрику")
    void arraysAreSavedByFactory() {
        Array arr1 = factory.createFromValues(1, 2);
        Array arr2 = factory.createEmpty(3);

        assertThat(repository.findAll()).hasSize(2);
        assertThat(repository.findById(arr1.getId())).hasValue(arr1);
        assertThat(repository.findById(arr2.getId())).hasValue(arr2);
    }

    @Test
    @DisplayName("save сохраняет массив и возвращает его")
    void saveStoresArray() {
        Array arr = factory.createFromValues(1, 2);

        assertThat(repository.findById(1L)).hasValue(arr);
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("findAll возвращает неизменяемый список")
    void findAllIsImmutable() {
        Array arr = factory.createFromValues(1);
        assertThatThrownBy(() -> repository.findAll().add(factory.createFromValues(2)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("findById возвращает Optional.empty для несуществующего ID")
    void findByIdReturnsEmptyForMissingId() {
        assertThat(repository.findById(999L)).isEmpty();
    }

    @Test
    @DisplayName("deleteById удаляет массив")
    void deleteByIdRemovesArray() {
        Array arr = factory.createFromValues(1);
        repository.deleteById(arr.getId());
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("generateNextId выдаёт уникальные ID")
    void generateNextIdIsUnique() {
        long id1 = repository.generateNextId();
        long id2 = repository.generateNextId();
        assertThat(id2).isEqualTo(id1 + 1);
    }

    @Test
    @DisplayName("clear очищает хранилище и сбрасывает ID")
    void clearResetsState() {
        factory.createFromValues(1);
        repository.clear();
        assertThat(repository.findAll()).isEmpty();
        assertThat(repository.generateNextId()).isEqualTo(1L);
    }
}

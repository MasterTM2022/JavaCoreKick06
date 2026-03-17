package arrayApp.entity;

import arrayApp.exception.ArrayException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ArrayTest {

    @Test
    @DisplayName("Создание массива с ID и значениями")
    void shouldCreateWithIdAndValues() {
        long id = 42L;
        int[] values = {1, 2, 3};
        Array arr = new Array(id, values);

        assertThat(arr.getId()).isEqualTo(42L);
        assertThat(arr.size()).isEqualTo(3);
        assertThat(arr.get(0)).isEqualTo(1);
        assertThat(arr.get(1)).isEqualTo(2);
        assertThat(arr.get(2)).isEqualTo(3);
    }

    @Test
    @DisplayName("toArray возвращает копию")
    void toArrayReturnsCopy() {
        Array arr = new Array(1L, new int[]{1, 2, 3});
        int[] copy = arr.toArray();
        assertThat(copy).isNotSameAs(new int[]{1, 2, 3}); // не та же ссылка
        assertThat(copy).containsExactly(1, 2, 3);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10})
    @DisplayName("Исключение при отрицательном размере")
    void shouldThrowOnNegativeSize(int size) {
        assertThatThrownBy(() -> new Array(1L, size))
                .isInstanceOf(ArrayException.class)
                .hasMessageContaining("negative");
    }

    @Test
    @DisplayName("Исключение при null-значениях")
    void shouldThrowOnNullValues() {
        assertThatThrownBy(() -> new Array(1L, (int[]) null))
                .isInstanceOf(ArrayException.class)
                .hasMessageContaining("null");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 5, 10})
    @DisplayName("Исключение при выходе за границы индекса")
    void shouldThrowOnInvalidIndex(int index) {
        Array arr = new Array(1L, new int[]{1, 2, 3});
        assertThatThrownBy(() -> arr.get(index))
                .isInstanceOf(ArrayException.class)
                .hasMessageContaining("out of bounds");
        assertThatThrownBy(() -> arr.set(index, 0))
                .isInstanceOf(ArrayException.class)
                .hasMessageContaining("out of bounds");
    }

    @Test
    @DisplayName("set корректно изменяет значение")
    void setShouldWork() {
        Array arr = new Array(1L, new int[]{1, 2, 3});
        arr.set(1, 99);
        assertThat(arr.get(1)).isEqualTo(99);
    }

}

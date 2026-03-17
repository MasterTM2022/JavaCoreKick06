package arrayApp.repository.comparator;

import arrayApp.entity.Array;

import java.util.Comparator;

public final class ArrayComparators {

    // === Сортировка по ID ===
    public static Comparator<Array> byId() {
        return Comparator.comparingLong(Array::getId);
    }

    // === Сортировка по количеству элементов ===
    public static Comparator<Array> bySize() {
        return Comparator.comparingInt(Array::size);
    }

    // === Сортировка по первому элементу ===
    public static Comparator<Array> byFirstElement() {
        return (a, b) -> {
            if (a.isEmpty() && b.isEmpty()) return 0;
            if (a.isEmpty()) return -1; // пустые в начало
            if (b.isEmpty()) return 1;
            return Integer.compare(a.get(0), b.get(0));
        };
    }

    // === Сортировка по сумме элементов ===
    public static Comparator<Array> bySum() {
        return Comparator.comparingInt(ArrayComparators::sum);
    }

    // === Сортировка по среднему значению ===
    public static Comparator<Array> byAverage() {
        return Comparator.comparingDouble(ArrayComparators::average);
    }

    // === Вспомогательные методы ===
    private static int sum(Array arr) {
        int s = 0;
        for (int i = 0; i < arr.size(); i++) {
            s += arr.get(i);
        }
        return s;
    }

    private static double average(Array arr) {
        return arr.isEmpty() ? 0.0 : (double) sum(arr) / arr.size();
    }

    // === Комбинированная сортировка: например, по size, затем по первому элементу ===
    public static Comparator<Array> bySizeThenById() {
        return bySize().thenComparing(byFirstElement());
    }
}

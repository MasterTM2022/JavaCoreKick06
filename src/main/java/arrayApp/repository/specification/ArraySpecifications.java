package arrayApp.repository.specification;

import arrayApp.entity.Array;
import arrayApp.service.ArrayService;
import arrayApp.service.DefaultArrayService;

public final class ArraySpecifications {
//    // Lazy-инициализация сервиса
//    private static final ArrayService SERVICE = new DefaultArrayService(null); // фабрика для аналитики не нужна!

    // Вспомогательный метод для вычисления суммы (чтобы не зависеть от сервиса)
    private static int sum(Array arr) {
        int s = 0;
        for (int i = 0; i < arr.size(); i++) {
            s += arr.get(i);
        }
        return s;
    }

    private static double average(Array arr) {
        return arr.size() == 0 ? 0 : (double) sum(arr) / arr.size();
    }

    private static int max(Array arr) {
        if (arr.size() == 0) {
            throw new IllegalArgumentException("Empty array");
        }
        int m = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            int v = arr.get(i);
            if (v > m) {
                m = v;
            }
        }
        return m;
    }

    private static int min(Array arr) {
        if (arr.size() == 0) {
            throw new IllegalArgumentException("Empty array");
        }
        int m = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            int v = arr.get(i);
            if (v < m) {
                m = v;
            }
        }
        return m;
    }

    // === Спецификации по ID ===
    public static ArraySpecification hasId(long id) {
        return arr -> arr.getId() == id;
    }

    // === Спецификации по количеству элементов ===
    public static ArraySpecification sizeEquals(int size) {
        return arr -> arr.size() == size;
    }

    public static ArraySpecification sizeGreaterThan(int size) {
        return arr -> arr.size() > size;
    }

    public static ArraySpecification sizeLessThan(int size) {
        return arr -> arr.size() < size;
    }

    // === Спецификации по сумме ===
    public static ArraySpecification sumEquals(int value) {
        return arr -> sum(arr) == value;
    }

    public static ArraySpecification sumGreaterThan(int value) {
        return arr -> sum(arr) > value;
    }

    public static ArraySpecification sumLessThan(int value) {
        return arr -> sum(arr) < value;
    }

    // === Спецификации по среднему ===
    public static ArraySpecification averageEquals(double value, double delta) {
        return arr -> Math.abs(average(arr) - value) <= delta;
    }

    public static ArraySpecification averageGreaterThan(double value) {
        return arr -> average(arr) > value;
    }

    public static ArraySpecification averageLessThan(double value) {
        return arr -> average(arr) < value;
    }

    // === Спецификации по максимуму ===
    public static ArraySpecification maxEquals(int value) {
        return arr -> !arr.isEmpty() && max(arr) == value;
    }

    public static ArraySpecification maxGreaterThan(int value) {
        return arr -> !arr.isEmpty() && max(arr) > value;
    }

    // === Спецификации по минимуму ===
    public static ArraySpecification minEquals(int value) {
        return arr -> !arr.isEmpty() && min(arr) == value;
    }

    public static ArraySpecification minLessThan(int value) {
        return arr -> !arr.isEmpty() && min(arr) < value;
    }

    // === Утилиты для комбинирования ===
    public static ArraySpecification and(ArraySpecification... specs) {
        return arr -> {
            for (ArraySpecification spec : specs) {
                if (!spec.test(arr)) {
                    return false;
                }
            }
            return true;
        };
    }

    public static ArraySpecification or(ArraySpecification... specs) {
        return arr -> {
            for (ArraySpecification spec : specs) {
                if (spec.test(arr)) {
                    return true;
                }
            }
            return false;
        };
    }
}

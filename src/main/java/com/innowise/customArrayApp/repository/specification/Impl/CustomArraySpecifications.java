package com.innowise.customArrayApp.repository.specification.Impl;

import com.innowise.customArrayApp.entity.CustomArray;
import com.innowise.customArrayApp.exception.CustomArrayException;
import com.innowise.customArrayApp.repository.specification.CustomArraySpecification;
import com.innowise.customArrayApp.util.ArrayStatisticsUtils;

public final class CustomArraySpecifications {

    private static int sum(CustomArray arr) {
        return ArrayStatisticsUtils.sum(arr.toArray());
    }

    private static double average(CustomArray arr) {
        return arr.size() == 0 ? 0 : (double) sum(arr) / arr.size();
    }

    private static int max(CustomArray arr) {
        if (arr.size() == 0) {
            throw new CustomArrayException("Empty array");
        }

        return ArrayStatisticsUtils.max(arr.toArray());
    }

    private static int min(CustomArray arr) {
        if (arr.size() == 0) {
            throw new CustomArrayException("Empty array");
        }
        return ArrayStatisticsUtils.min(arr.toArray());
    }

    // === Спецификации по ID ===
    public static CustomArraySpecification hasId(long id) {
        return arr -> arr.getId() == id;
    }

    // === Спецификации по количеству элементов ===
    public static CustomArraySpecification sizeEquals(int size) {
        return arr -> arr.size() == size;
    }

    public static CustomArraySpecification sizeGreaterThan(int size) {
        return arr -> arr.size() > size;
    }

    public static CustomArraySpecification sizeLessThan(int size) {
        return arr -> arr.size() < size;
    }

    // === Спецификации по сумме ===
    public static CustomArraySpecification sumEquals(int value) {
        return arr -> sum(arr) == value;
    }

    public static CustomArraySpecification sumGreaterThan(int value) {
        return arr -> sum(arr) > value;
    }

    public static CustomArraySpecification sumLessThan(int value) {
        return arr -> sum(arr) < value;
    }

    // === Спецификации по среднему ===
    public static CustomArraySpecification averageEquals(double value, double delta) {
        return arr -> Math.abs(average(arr) - value) <= delta;
    }

    public static CustomArraySpecification averageGreaterThan(double value) {
        return arr -> average(arr) > value;
    }

    public static CustomArraySpecification averageLessThan(double value) {
        return arr -> average(arr) < value;
    }

    // === Спецификации по максимуму ===
    public static CustomArraySpecification maxEquals(int value) {
        return arr -> !arr.isEmpty() && max(arr) == value;
    }

    public static CustomArraySpecification maxGreaterThan(int value) {
        return arr -> !arr.isEmpty() && max(arr) > value;
    }

    // === Спецификации по минимуму ===
    public static CustomArraySpecification minEquals(int value) {
        return arr -> !arr.isEmpty() && min(arr) == value;
    }

    public static CustomArraySpecification minLessThan(int value) {
        return arr -> !arr.isEmpty() && min(arr) < value;
    }

    // === Утилиты для комбинирования ===
    public static CustomArraySpecification and(CustomArraySpecification... specs) {
        return arr -> {
            for (CustomArraySpecification spec : specs) {
                if (!spec.test(arr)) {
                    return false;
                }
            }
            return true;
        };
    }

    public static CustomArraySpecification or(CustomArraySpecification... specs) {
        return arr -> {
            for (CustomArraySpecification spec : specs) {
                if (spec.test(arr)) {
                    return true;
                }
            }
            return false;
        };
    }
}
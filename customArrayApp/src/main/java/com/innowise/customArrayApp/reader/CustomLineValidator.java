package com.innowise.customArrayApp.reader;

import java.util.List;


public interface CustomLineValidator {
    public List<Integer> parseValidLine(String line);

    default boolean isValidInteger(String s) {
        if (s == null || s.isBlank()) return false;
        // Допускаем отрицательные числа: "-123"
        return s.matches("-?\\d+");
    }
}

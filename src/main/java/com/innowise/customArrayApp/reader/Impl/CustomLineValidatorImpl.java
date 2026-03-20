package com.innowise.customArrayApp.reader.Impl;

import com.innowise.customArrayApp.reader.CustomLineValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomLineValidatorImpl implements CustomLineValidator {
    // Разрешённые разделители: ; , пробел
    private static final Pattern PATTERN_DELIMITER = Pattern.compile("[;,\\s]+");

    public List<Integer> parseValidLine(String line) {
        if (line == null || line.isBlank()) {
            return new ArrayList<>();
        }

        String[] tokens = PATTERN_DELIMITER.split(line.strip());
        List<Integer> numbers = new ArrayList<>();

        for (String token : tokens) {
            if (token.isBlank()) continue;

            // Проверяем, что токен — это целое число (возможно, отрицательное)
            if (!isValidInteger(token)) {
                return new ArrayList<>(); // некорректная строка
            }

            try {
                numbers.add(Integer.parseInt(token));
            } catch (NumberFormatException e) {
                return new ArrayList<>(); // переполнение и т.п.
            }
        }

        return numbers;
    }
}

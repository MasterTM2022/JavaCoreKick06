package com.innowise.customArrayApp.reader;

import com.innowise.customArrayApp.reader.Impl.CustomLineValidatorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LineValidatorTest {
    private final CustomLineValidatorImpl validator = new CustomLineValidatorImpl();

    static Stream<Arguments> validLines() {
        return Stream.of(
                Arguments.of("1,2,3", Arrays.asList(1, 2, 3)),
                Arguments.of("1; 2; 3", Arrays.asList(1, 2, 3)),
                Arguments.of("10 20 30", Arrays.asList(10, 20, 30)),
                Arguments.of("-5, 10", Arrays.asList(-5, 10)),
                Arguments.of("", new ArrayList<>()), // пустая строка → пустой массив
                Arguments.of("   ", new ArrayList<>())
        );
    }

    static Stream<Arguments> invalidLines() {
        return Stream.of(
                Arguments.of("1a2"),
                Arguments.of("6..5"),
                Arguments.of("abc"),
                Arguments.of("1, 2, x"),
                Arguments.of("--10")
        );
    }

    @ParameterizedTest
    @MethodSource("validLines")
    @DisplayName("Валидные строки парсятся корректно")
    void validLinesParseCorrectly(String input, List<Integer> expected) {
        assertThat(validator.parseValidLine(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("invalidLines")
    @DisplayName("Некорректные строки возвращают null")
    void invalidLinesReturnNull(String input) {
        assertThat(validator.parseValidLine(input)).isEmpty();
    }

    @Test
    @DisplayName("null-вход возвращает пустой массив")
    void nullInputReturnsNull() {
        assertThat(validator.parseValidLine(null)).isEmpty();
    }
}

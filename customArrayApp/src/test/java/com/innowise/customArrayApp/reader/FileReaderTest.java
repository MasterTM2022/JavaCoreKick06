package com.innowise.customArrayApp.reader;

import com.innowise.customArrayApp.reader.Impl.CustomFileReaderImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileReaderTest {
    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Reading of correct file")
    void readsValidFile() throws IOException {
        // Создаём временный файл
        Path file = tempDir.resolve("test.txt");
        Files.write(file, Arrays.asList(
                "1,2,3",
                "1; 2; x3; 6..5; 77",
                "",
                "11; 2",
                "abc"
        ));

        CustomFileReaderImpl reader = new CustomFileReaderImpl();
        List<List<Integer>> result = reader.readValidArrays(file.toString());

        assertThat(result).containsExactly(
                Arrays.asList(1, 2, 3),
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(11, 2),
                Arrays.asList()
        );
    }

    @Test
    @DisplayName("Error if file is absent")
    void throwsExceptionOnMissingFile() {
        CustomFileReaderImpl reader = new CustomFileReaderImpl();
        assertThatThrownBy(() -> reader.readValidArrays("nonexistent.txt"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error of reading file: " + "nonexistent.txt");
    }
}

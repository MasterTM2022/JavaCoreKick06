package com.innowise.customArrayApp.reader.Impl;

import com.innowise.customArrayApp.reader.CustomFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CustomFileReaderImpl implements CustomFileReader {
    private final CustomLineValidatorImpl validator = new CustomLineValidatorImpl();

    public List<List<Integer>> readValidArrays(String filePath) {
        Path path = Paths.get(filePath);
        List<List<Integer>> validArrays = new ArrayList<>();

        try {
            Files.lines(path)
                    .forEach(line -> {
                        List<Integer> parsed = validator.parseValidLine(line);
                        if (parsed != null) {
                            validArrays.add(parsed);
                        }
                        // Некорректные строки просто игнорируем
                    });
        } catch (IOException e) {
            throw new RuntimeException("Error of reading file: " + filePath, e);
        }

        return validArrays;
    }
}

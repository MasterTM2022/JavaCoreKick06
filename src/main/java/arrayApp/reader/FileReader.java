package arrayApp.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private final LineValidator validator = new LineValidator();

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
            throw new RuntimeException("Ошибка чтения файла: " + filePath, e);
        }

        return validArrays;
    }
}

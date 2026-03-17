package arrayApp.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LineValidator {
    // Разрешённые разделители: ; , пробел
    private static final Pattern DELIMITER = Pattern.compile("[;,\\s]+");

    public List<Integer> parseValidLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null; // пустая строка → пропускаем, не обрабатываем
        }

        String[] tokens = DELIMITER.split(line.trim());
        List<Integer> numbers = new ArrayList<>();

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            // Проверяем, что токен — это целое число (возможно, отрицательное)
            if (!isValidInteger(token)) {
                return null; // некорректная строка
            }

            try {
                numbers.add(Integer.parseInt(token));
            } catch (NumberFormatException e) {
                return null; // переполнение и т.п.
            }
        }

        return numbers;
    }

    private boolean isValidInteger(String s) {
        if (s == null || s.isEmpty()) return false;
        // Допускаем отрицательные числа: "-123"
        return s.matches("-?\\d+");
    }
}

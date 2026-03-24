package com.innowise.textHandler;

import com.innowise.textHandler.entity.Impl.Text;
import com.innowise.textHandler.parser.Impl.ParagraphParserImpl;
import com.innowise.textHandler.parser.Impl.SentenceParserImpl;
import com.innowise.textHandler.parser.Impl.TextParserImpl;
import com.innowise.textHandler.service.TextAnalysisService;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class TextHandlerIntegrationTest {

    @Test
    void shouldProcessRealFileCorrectly() throws Exception {
        // Создаём тестовый файл
        String testContent = "Hello world!\n\nHow are you today? I am fine.";
        Files.write(Paths.get("target/test-input.txt"), testContent.getBytes());

        // Парсинг
        TextParserImpl textParser = new TextParserImpl();
        ParagraphParserImpl paragraphParser = new ParagraphParserImpl();
        SentenceParserImpl sentenceParser = new SentenceParserImpl();

        textParser.setNext(paragraphParser);
        paragraphParser.setNext(sentenceParser);

        String input = Files.readString(Paths.get("target/test-input.txt"));
        Text parsed = (Text) textParser.parse(input);

        // Проверка структуры
        assertThat(parsed.getChildren()).hasSize(2);

        // Анализ
        TextAnalysisService service = new TextAnalysisService();
        int maxCommon = service.findMaxSentencesWithCommonWords(parsed);
        assertThat(maxCommon).isGreaterThanOrEqualTo(1);

        // Перестановка слов
        Text swapped = service.swapFirstLastTokens(parsed);
        assertThat(swapped.getText()).isNotEmpty();
    }
}

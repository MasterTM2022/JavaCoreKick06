package com.innowise.textHandler.service;

import com.innowise.textHandler.entity.Impl.Sentence;
import com.innowise.textHandler.entity.Impl.Text;
import com.innowise.textHandler.parser.Impl.ParagraphParserImpl;
import com.innowise.textHandler.parser.Impl.SentenceParserImpl;
import com.innowise.textHandler.parser.Impl.TextParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TextAnalysisServiceTest {

    private TextAnalysisService service;
    private TextParserImpl parser;

    @BeforeEach
    void setUp() {
        service = new TextAnalysisService();
        parser = new TextParserImpl();
        ParagraphParserImpl paragraphParser = new ParagraphParserImpl();
        SentenceParserImpl sentenceParser = new SentenceParserImpl();

        parser.setNext(paragraphParser);
        paragraphParser.setNext(sentenceParser);
    }

    @Test
    void findMaxSentencesWithCommonWordsShouldReturnCorrectCount() {
        String input = "hello world\nworld hello\nbye";
        Text text = (Text) parser.parse(input);

        int result = service.findMaxSentencesWithCommonWords(text);
        assertThat(result).isEqualTo(2); // первые два предложения имеют одинаковые слова
    }

    @Test
    void sortSentencesByTokenCountShouldSortCorrectly() {
        String input = "a b c\nd e\nf";
        Text text = (Text) parser.parse(input);

        List<Sentence> result = service.sortSentencesByTokenCount(text);
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getChildren()).hasSize(1); // "f"
        assertThat(result.get(1).getChildren()).hasSize(2); // "d e"
        assertThat(result.get(2).getChildren()).hasSize(3); // "a b c"
    }

    @Test
    void swapFirstLastTokensShouldWorkCorrectly() {
        String input = "Hello world!\nGood morning everyone.";
        Text text = (Text) parser.parse(input);

        Text swapped = service.swapFirstLastTokens(text);
        String result = swapped.getText();

        assertThat(result).contains("World hello!");
        assertThat(result).contains("Everyone morning good.");
    }

    @Test
    void shouldHandleSingleWordSentences() {
        String input = "Hello!\nWorld.";
        Text text = (Text) parser.parse(input);

        Text swapped = service.swapFirstLastTokens(text);
        // Одно слово - должно остаться без изменений
        assertThat(swapped.getText()).isEqualTo("Hello!\nWorld.");
    }
}

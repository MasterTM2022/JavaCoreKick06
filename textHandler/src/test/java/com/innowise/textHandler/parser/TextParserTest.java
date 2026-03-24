package com.innowise.textHandler.parser;

import com.innowise.textHandler.entity.Impl.Paragraph;
import com.innowise.textHandler.entity.Impl.Sentence;
import com.innowise.textHandler.entity.Impl.Text;
import com.innowise.textHandler.parser.Impl.ParagraphParserImpl;
import com.innowise.textHandler.parser.Impl.SentenceParserImpl;
import com.innowise.textHandler.parser.Impl.TextParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextParserTest {
    private TextParserImpl textParser;

    @BeforeEach
    void setUp() {
        TextParserImpl textParser = new TextParserImpl();
        ParagraphParserImpl paragraphParser = new ParagraphParserImpl();
        SentenceParserImpl sentenceParser = new SentenceParserImpl();

        textParser.setNext(paragraphParser);
        paragraphParser.setNext(sentenceParser);

        this.textParser = textParser;
    }

    @Test
    void shouldParseSingleParagraphWithMultipleSentences() {
        String input = "Hello world! How are you?";
        Text result = (Text) textParser.parse(input);

        assertThat(result.getChildren()).hasSize(1);
        Paragraph paragraph = (Paragraph) result.getChildren().get(0);
        assertThat(paragraph.getChildren()).hasSize(2);

        Sentence s1 = (Sentence) paragraph.getChildren().get(0);
        assertThat(s1.getText()).isEqualTo("Hello world!");

        Sentence s2 = (Sentence) paragraph.getChildren().get(1);
        assertThat(s2.getText()).isEqualTo("How are you?");
    }

    @Test
    void shouldParseMultipleParagraphs() {
        String input = "First para.\n\nSecond para!";
        Text result = (Text) textParser.parse(input);

        assertThat(result.getChildren()).hasSize(2);
        assertThat(((Paragraph) result.getChildren().get(0)).getText()).isEqualTo("First para.");
        assertThat(((Paragraph) result.getChildren().get(1)).getText()).isEqualTo("Second para!");
    }

    @Test
    void shouldHandleEmptySentences() {
        String input = "Valid sentence. ... Invalid?   ";
        Text result = (Text) textParser.parse(input);

        Paragraph paragraph = (Paragraph) result.getChildren().get(0);
        // Пустые предложения должны быть отфильтрованы
        assertThat(paragraph.getChildren()).hasSize(2);
    }
}

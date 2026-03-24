package com.innowise.textHandler.entity;

import com.innowise.textHandler.entity.Impl.Paragraph;
import com.innowise.textHandler.entity.Impl.Sentence;
import com.innowise.textHandler.entity.Impl.Text;
import com.innowise.textHandler.entity.Impl.Word;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextComponentTest {
    @Test
    void wordShouldReturnCorrectTextAndCharCount() {
        Word word = new Word("Hello");
        assertThat(word.getText()).isEqualTo("Hello");
        assertThat(word.getCharCount()).isEqualTo(5);
    }

    @Test
    void sentenceShouldPreservePunctuation() {
        Sentence sentence = new Sentence("!");
        sentence.addToken(new Word("Hello"));
        sentence.addToken(new Word("world"));

        assertThat(sentence.getText()).isEqualTo("Hello world!");
        assertThat(sentence.getCharCount()).isEqualTo(12); // "Hello world" + "!"
    }

    @Test
    void paragraphShouldJoinSentencesWithSpace() {
        Paragraph paragraph = new Paragraph();
        Sentence s1 = new Sentence(".");
        s1.addToken(new Word("Hello"));
        Sentence s2 = new Sentence("!");
        s2.addToken(new Word("World"));

        paragraph.addSentence(s1);
        paragraph.addSentence(s2);

        assertThat(paragraph.getText()).isEqualTo("Hello. World!");
    }

    @Test
    void textShouldJoinParagraphsWithNewLines() {
        Text text = new Text();
        Paragraph p1 = new Paragraph();
        Sentence s1 = new Sentence(".");
        s1.addToken(new Word("Para1"));
        p1.addSentence(s1);

        Paragraph p2 = new Paragraph();
        Sentence s2 = new Sentence("!");
        s2.addToken(new Word("Para2"));
        p2.addSentence(s2);

        text.addParagraph(p1);
        text.addParagraph(p2);

        assertThat(text.getText()).isEqualTo("Para1.\nPara2!");
    }
}

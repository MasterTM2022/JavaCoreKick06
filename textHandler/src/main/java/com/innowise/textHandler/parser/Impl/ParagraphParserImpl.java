package com.innowise.textHandler.parser.Impl;

import com.innowise.textHandler.entity.Impl.Paragraph;
import com.innowise.textHandler.entity.Impl.Sentence;
import com.innowise.textHandler.entity.TextComponent;
import com.innowise.textHandler.parser.TextParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParserImpl extends TextParser {
    private static final Pattern SENTENCE_PATTERN = Pattern.compile("([^\\.!?]*?)([\\.!?]+|$)");


    @Override
    public TextComponent parse(String text) {
        Paragraph result = new Paragraph();
        Matcher matcher = SENTENCE_PATTERN.matcher(text.strip());

        while (matcher.find()) {
            String sentenceText = matcher.group(1).strip();
            String punctuation = matcher.group(2).strip();

            if (sentenceText.isEmpty()) {
                continue;
            }

            Sentence sentence = new Sentence(punctuation);
            if (nextParser != null) {
                TextComponent tokens = nextParser.parse(sentenceText);
                if (tokens instanceof Sentence) {
                    Sentence tokenSentence = (Sentence) tokens;
                    for (TextComponent token : tokenSentence.getChildren()) {
                        sentence.addToken(token);
                    }
                    result.addSentence(sentence);
                }
            }
        }
        return result;
    }
}

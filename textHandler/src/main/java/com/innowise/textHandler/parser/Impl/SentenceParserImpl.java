package com.innowise.textHandler.parser.Impl;

import com.innowise.textHandler.entity.Impl.Sentence;
import com.innowise.textHandler.entity.Impl.Word;
import com.innowise.textHandler.entity.TextComponent;
import com.innowise.textHandler.parser.TextParser;

public class SentenceParserImpl extends TextParser {
    private static final String TOKEN_REGEX = "\\s+"; // лексемы (whitespace-separated)

    @Override
    public TextComponent parse(String text) {
        Sentence result = new Sentence("");
        String[] tokens = text.split(TOKEN_REGEX);

        for (String token : tokens) {
            if (!token.isEmpty()) {
                Word word = new Word(token);
                result.addToken(word);
            }
        }
        return result;
    }
}

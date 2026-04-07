package com.innowise.textHandler.entity;

import java.util.regex.Pattern;

public enum TextType {
//    SYMBOL(null, "", null),
//    WORD(null, "", SYMBOL),
    WORD(null, "", null),
    LEXEMA("\\s+", " ", WORD),
    SENTENCE("([^.!?]*?)([.!?]+|$)", " ", LEXEMA),
    PARAGRAPH("\\R", "\r\n", SENTENCE),
    TEXT("\\R{2,}", "\r\n\r\n", PARAGRAPH);

    private final String parseDelimiterRegex;
    private final String reconstructionDelimiter;
    private final TextType child;

    TextType(String parseDelimiterRegex, String reconstructionDelimiter, TextType child) {
        this.parseDelimiterRegex = parseDelimiterRegex;
        this.reconstructionDelimiter = reconstructionDelimiter;
        this.child = child;
    }

    // Метод для получения скомпилированного паттерна (для парсинга)
    public Pattern getParsePattern() {
        return parseDelimiterRegex != null ? Pattern.compile(parseDelimiterRegex) : null;
    }

    // Метод для получения разделителя при восстановлении текста
    public String getReconstructionDelimiter() {
        return reconstructionDelimiter;
    }

    public TextType getChild() {
        return child;
    }

    public boolean isLeaf() {
        return this == WORD;
    }

}
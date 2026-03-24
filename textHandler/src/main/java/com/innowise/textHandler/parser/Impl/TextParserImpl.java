package com.innowise.textHandler.parser.Impl;

import com.innowise.textHandler.entity.Impl.Paragraph;
import com.innowise.textHandler.entity.Impl.Text;
import com.innowise.textHandler.entity.TextComponent;
import com.innowise.textHandler.parser.TextParser;

public class TextParserImpl extends TextParser {
    private static final String PARAGRAPH_REGEX = "\\R{1,}"; // 1+ line separators

    @Override
    public TextComponent parse(String text) {
        Text result = new Text();
        String[] paragraphs = text.split(PARAGRAPH_REGEX);

        for (String paragraph : paragraphs) {
            if (!paragraph.isBlank() && nextParser != null) {
                Paragraph p = (Paragraph) nextParser.parse(paragraph.strip());
                result.addParagraph(p);
            }
        }
        return result;
    }
}

package com.innowise.textHandler.parser.Impl;


import com.innowise.textHandler.entity.Impl.UniversalTextComponent;
import com.innowise.textHandler.entity.TextComponent;
import com.innowise.textHandler.entity.TextType;
import com.innowise.textHandler.parser.TextParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniversalTextParser extends TextParser {

    public TextComponent parse(String text, TextType type) {
        if (text == null || text.isEmpty()) {
            return new UniversalTextComponent(type, "");
        }

        if (type.isLeaf()) {
            return new UniversalTextComponent(type, text);
        }

        switch (type) {
            case TEXT:
                String[] paragraphs = text.split("\\R{2,}", -1);
                List<TextComponent> paragraphComponents = new ArrayList<>();
                for (String para : paragraphs) {
                    if (!para.strip().isEmpty()) {
                        paragraphComponents.add(parse(para.strip(), TextType.PARAGRAPH));
                    }
                }
                return new UniversalTextComponent(TextType.TEXT, "", paragraphComponents);

            case PARAGRAPH:
                // Разбиваем параграф на предложения
                List<TextComponent> sentenceComponents = new ArrayList<>();
                Pattern sentencePattern = Pattern.compile("([^\\.!?]*?)([\\.!?]+|$)");
                Matcher matcher = sentencePattern.matcher(text.strip());

                while (matcher.find()) {
                    String sentenceText = matcher.group(1).strip();
                    String punctuation = matcher.group(2);

                    if (!sentenceText.isEmpty() || !punctuation.isEmpty()) {
                        // Разбиваем предложение на лексемы
                        List<TextComponent> lexemeComponents = new ArrayList<>();
                        if (!sentenceText.isEmpty()) {
                            String[] lexemes = sentenceText.split("\\s+");
                            for (String lexeme : lexemes) {
                                if (!lexeme.isEmpty()) {
                                    lexemeComponents.add(parse(lexeme, TextType.LEXEMA));
                                }
                            }
                        }
                        sentenceComponents.add(new UniversalTextComponent(TextType.SENTENCE, punctuation, lexemeComponents));
                    }
                }
                return new UniversalTextComponent(TextType.PARAGRAPH, "", sentenceComponents);

            case LEXEMA:
                // Извлекаем пунктуацию из конца лексемы
                Pattern lexemePattern = Pattern.compile("^(.*?)(\\p{P}*)$");
                Matcher lexemeMatcher = lexemePattern.matcher(text);
                if (lexemeMatcher.matches()) {
                    String wordPart = lexemeMatcher.group(1);
                    String punctuationPart = lexemeMatcher.group(2);
                    List<TextComponent> wordComponents = new ArrayList<>();
                    if (!wordPart.isEmpty()) {
                        wordComponents.add(new UniversalTextComponent(TextType.WORD, wordPart));
                    }
                    return new UniversalTextComponent(TextType.LEXEMA, punctuationPart, wordComponents);
                }
                return new UniversalTextComponent(TextType.LEXEMA, "", Arrays.asList(new UniversalTextComponent(TextType.WORD, text)));

            case WORD:
                return new UniversalTextComponent(TextType.WORD, text);

            default:
                return new UniversalTextComponent(type, text);
        }
    }
}
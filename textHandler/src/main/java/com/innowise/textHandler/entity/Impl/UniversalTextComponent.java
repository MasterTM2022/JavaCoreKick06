package com.innowise.textHandler.entity.Impl;

import com.innowise.textHandler.entity.TextComponent;
import com.innowise.textHandler.entity.TextType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UniversalTextComponent implements TextComponent {
    private final TextType type;
    private final String content; // для листов (SYMBOL, WORD)
    private final List<TextComponent> children = new ArrayList<>();
    private final String trailingPunctuation; // для LEXEMA и SENTENCE

    // Конструктор для листьев
    public UniversalTextComponent(TextType type, String content) {
        this(type, content, "", Collections.emptyList());
    }

    // Конструктор для композитов с пунктуацией
    public UniversalTextComponent(TextType type, String trailingPunctuation, List<TextComponent> children) {
        this(type, "", trailingPunctuation, children);
    }

    // Приватный конструктор
    private UniversalTextComponent(TextType type, String content, String trailingPunctuation, List<TextComponent> children) {
        this.type = type;
        this.content = content != null ? content : "";
        this.trailingPunctuation = trailingPunctuation != null ? trailingPunctuation : "";
        this.children.addAll(children);
    }

    @Override
    public TextType getType() {
        return type;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(children);
    }

    // НОВЫЙ МЕТОД!
    public String getTrailingPunctuation() {
        return trailingPunctuation;
    }

    @Override
    public String getText() {
        if (type.isLeaf()) {
            return content;
        }

        if (type == TextType.SENTENCE) {
            String childrenText = children.stream()
                    .map(child -> {
                        String childText = child.getText();
                        return childText;
                    })
                    .collect(Collectors.joining(" "));

            return childrenText + trailingPunctuation;
        }

        if (type == TextType.LEXEMA) {
            String childrenText = children.stream()
                    .map(child -> {
                        String childText = child.getText();
                        return childText;
                    })
                    .collect(Collectors.joining(""));
            return childrenText + trailingPunctuation;
        }

        return children.stream()
                .map(TextComponent::getText)
                .collect(Collectors.joining(type.getReconstructionDelimiter()));
    }

    @Override
    public int getCharCount() {
        if (type.isLeaf()) {
            return content.length();
        }

        // Для SENTENCE: считаем символы всех лексем + пунктуацию + //пробелы между ними
        if (type == TextType.SENTENCE) {
            int childrenChars = children.stream().mapToInt(TextComponent::getCharCount).sum();
//            int spacesBetweenLexemes = Math.max(0, children.size() - 1);
//            return childrenChars + spacesBetweenLexemes + trailingPunctuation.length();
            return childrenChars + trailingPunctuation.length();
        }

        // Для LEXEMA: слово + пунктуация (без пробелов)
        if (type == TextType.LEXEMA) {
            int childrenChars = children.stream().mapToInt(TextComponent::getCharCount).sum();
            return childrenChars + trailingPunctuation.length();
        }

        // Для остальных уровней: используем разделитель из enum
        int childrenChars = children.stream().mapToInt(TextComponent::getCharCount).sum();
        if (!children.isEmpty()) {
//            Если пробелы не учитываем между элементами уровня
//            int delimiterLength = type.getReconstructionDelimiter().length();
//            int delimitersCount = children.size() - 1;
//            return childrenChars + (delimiterLength * delimitersCount);
            return childrenChars;
        }

            return 0;
        }
    }
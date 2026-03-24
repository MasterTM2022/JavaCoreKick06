package com.innowise.textHandler.entity.Impl;

import com.innowise.textHandler.entity.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Text implements TextComponent {
    private final List<Paragraph> paragraphs = new ArrayList<>();

    public void addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
    }

    @Override
    public String getText() {
        return paragraphs.stream()
                .map(Paragraph::getText)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public int getCharCount() {
        return paragraphs.stream().mapToInt(TextComponent::getCharCount).sum();
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(paragraphs);
    }
}

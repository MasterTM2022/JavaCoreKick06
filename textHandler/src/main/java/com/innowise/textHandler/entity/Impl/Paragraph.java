package com.innowise.textHandler.entity.Impl;

import com.innowise.textHandler.entity.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Paragraph implements TextComponent {
    private final List<Sentence> sentences = new ArrayList<>();

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
    }

    @Override
    public String getText() {
        return sentences.stream()
                .map(Sentence::getText)
                .collect(Collectors.joining(" "));
    }

    @Override
    public int getCharCount() {
        return sentences.stream().mapToInt(TextComponent::getCharCount).sum();
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(sentences);
    }
}

package com.innowise.textHandler.entity.Impl;

import com.innowise.textHandler.entity.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sentence implements TextComponent {
    private final List<TextComponent> tokens = new ArrayList<>(); // лексемы
    private final String endingPunctuation;

    public Sentence(String endingPunctuation) {
        this.endingPunctuation = endingPunctuation != null ? endingPunctuation : "";
    }

    public void addToken(TextComponent token) {
        tokens.add(token);
    }

    public String getEndingPunctuation() {
        return endingPunctuation;
    }

    @Override
    public String getText() {
        return tokens.stream()
                .map(TextComponent::getText)
                .collect(Collectors.joining(" ")) + endingPunctuation;
    }

    @Override
    public int getCharCount() {
        int tokensCount = tokens.stream().mapToInt(TextComponent::getCharCount).sum();
        int spacesCount = Math.max(0, tokens.size() - 1);
        int punctuationCount = endingPunctuation.length();
        return tokensCount + spacesCount + punctuationCount;
    }

    @Override
    public List<TextComponent> getChildren() { return new ArrayList<>(tokens); }
}

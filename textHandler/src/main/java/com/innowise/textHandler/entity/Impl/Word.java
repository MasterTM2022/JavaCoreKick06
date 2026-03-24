package com.innowise.textHandler.entity.Impl;


import com.innowise.textHandler.entity.TextComponent;

import java.util.Collections;
import java.util.List;

public final class Word implements TextComponent {
    private final String text;

    public Word(String text) {
        this.text = text.trim();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getCharCount() {
        return text.length();
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }
}

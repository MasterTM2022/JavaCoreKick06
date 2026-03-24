package com.innowise.textHandler.entity.Impl;

import com.innowise.textHandler.entity.TextComponent;

import java.util.Collections;
import java.util.List;

public final class Symbol implements TextComponent {
    private final char symbol;

    public Symbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getText() { return String.valueOf(symbol); }

    @Override
    public int getCharCount() { return 1; }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }
}

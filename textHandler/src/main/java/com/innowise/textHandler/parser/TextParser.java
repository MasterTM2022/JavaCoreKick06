package com.innowise.textHandler.parser;

import com.innowise.textHandler.entity.TextComponent;

public abstract class TextParser {
    protected TextParser nextParser;

    public void setNext(TextParser next) {
        this.nextParser = next;
    }

    public abstract TextComponent parse(String text);
}

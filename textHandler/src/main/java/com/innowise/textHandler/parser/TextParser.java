package com.innowise.textHandler.parser;

import com.innowise.textHandler.entity.TextComponent;
import com.innowise.textHandler.entity.TextType;

public abstract class TextParser {

   public abstract TextComponent parse(String text, TextType type);
}

package com.innowise.textHandler.entity;

import java.util.List;

public interface TextComponent {
    String getText(); // восстановление исходного текста

    int getCharCount(); // подсчёт символов

    List<TextComponent> getChildren(); // для Composite
}

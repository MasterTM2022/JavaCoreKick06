package com.innowise.textHandler.entity;

import java.util.List;

public interface TextComponent {
    TextType getType(); // тип компонента

    String getContent(); // собственный текст (для листьев) или пусто (для композитов)

    List<TextComponent> getChildren(); // для композитов — дети, для листьев — пустой список

    String getText(); // восстановление полного текста

    int getCharCount(); // подсчёт всех символов (включая пунктуацию //и пробелы)
}
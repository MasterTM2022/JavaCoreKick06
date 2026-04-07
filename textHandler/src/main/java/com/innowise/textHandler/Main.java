package com.innowise.textHandler;

import com.innowise.textHandler.entity.TextComponent;
import com.innowise.textHandler.entity.TextType;
import com.innowise.textHandler.parser.Impl.UniversalTextParser;
import com.innowise.textHandler.service.TextAnalysisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {

            // Чтение файла
            String text = Files.readString(Paths.get("input/input2.txt"));

            // Парсинг с универсальным парсером
            UniversalTextParser parser = new UniversalTextParser();
            TextComponent parsedText = parser.parse(text, TextType.TEXT);

            String restoredText = parsedText.getText();
            logger.info("Restored text length: {}", restoredText.length());
            logger.info("Restored text:\n{}", restoredText);

            logger.info("Parsed text char count (spaces are uncountable): {}", parsedText.getCharCount());

            // Анализ
            TextAnalysisService service = new TextAnalysisService();

            // Задача 1
            int maxCommon = service.findMaxSentencesWithCommonWords(parsedText);
            logger.info("Max sentences with common words: {}", maxCommon > 1 ? maxCommon : 0);

            // Задача 2
            List<TextComponent> sorted = service.sortSentencesByWordCount(parsedText);
            logger.info("Sorted sentences (by word numbers): \n{}", sorted.stream()
                    .map(TextComponent::getText)
                    .collect(Collectors.joining("\n")));

            // Задача 3
            TextComponent swapped = service.swapFirstLastTokens(parsedText);
            logger.info("Text after swap: \n{}", swapped.getText());

        } catch (IOException e) {
            logger.error("Error reading file", e);
        }
    }
}
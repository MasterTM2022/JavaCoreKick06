package com.innowise.textHandler.service;

import com.innowise.textHandler.entity.TextComponent;
import com.innowise.textHandler.entity.Impl.UniversalTextComponent;
import com.innowise.textHandler.entity.TextType;
import com.innowise.textHandler.util.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextAnalysisService {
    private static final Logger logger = LogManager.getLogger(TextAnalysisService.class);

    // Задача 1: Найти наибольшее количество предложений с одинаковыми словами
    public int findMaxSentencesWithCommonWords(TextComponent text) {
        List<TextComponent> sentences = getAllComponentsByType(text, TextType.SENTENCE);
        Map<Set<String>, Integer> wordSetCounts = new HashMap<>();

        for (TextComponent sentence : sentences) {
            Set<String> words = extractWordsFromSentence(sentence);
            if (!words.isEmpty()) {
                wordSetCounts.merge(words, 1, Integer::sum);
            }
        }

        return wordSetCounts.values().stream()
                .max(Integer::compareTo)
                .orElse(0);
    }

    // Задача 2: Сортировка предложений по количеству слов
    public List<TextComponent> sortSentencesByWordCount(TextComponent text) {
        return getAllComponentsByType(text, TextType.SENTENCE).stream()
                .sorted(Comparator.comparingInt(this::getWordCount))
                .collect(Collectors.toList());
    }

    private int getWordCount(TextComponent sentence) {
        return getAllComponentsByType(sentence, TextType.WORD).size();
    }

    // Задача 3: Поменять первую и последнюю лексему в каждом предложении
    public TextComponent swapFirstLastTokens(TextComponent text) {
        if (text.getType() == TextType.TEXT) {
            List<TextComponent> newParagraphs = new ArrayList<>();
            for (TextComponent paragraph : text.getChildren()) {
                newParagraphs.add(swapFirstLastTokens(paragraph));
            }
            return new UniversalTextComponent(TextType.TEXT, "", newParagraphs);
        }

        if (text.getType() == TextType.PARAGRAPH) {
            List<TextComponent> newSentences = new ArrayList<>();
            for (TextComponent sentence : text.getChildren()) {
                newSentences.add(swapFirstLastInSentence(sentence));
            }
            return new UniversalTextComponent(TextType.PARAGRAPH, "", newSentences);
        }

        // Для других типов возвращаем как есть
        return text;
    }

    // Вспомогательные методы

    private Set<String> extractWordsFromSentence(TextComponent sentence) {
        return getAllComponentsByType(sentence, TextType.WORD).stream()
                .map(TextComponent::getText)
                .map(token -> token.toLowerCase().replaceAll("[^a-zа-яё]", ""))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toSet());
    }

    private TextComponent swapFirstLastInSentence(TextComponent sentence) {
        if (sentence.getType() != TextType.SENTENCE) {
            return sentence;
        }

        List<TextComponent> lexemes = sentence.getChildren();
        if (lexemes.size() < 2) {
            return sentence;
        }

        TextComponent firstLexeme = lexemes.get(0);
        TextComponent lastLexeme = lexemes.get(lexemes.size() - 1);

        TextComponent newFirstLexeme = createSwappedLexeme(lastLexeme, true);
        TextComponent newLastLexeme = createSwappedLexeme(firstLexeme, false);

        List<TextComponent> newLexemes = new ArrayList<>();
        newLexemes.add(newFirstLexeme);
        for (int i = 1; i < lexemes.size() - 1; i++) {
            newLexemes.add(lexemes.get(i));
        }
        newLexemes.add(newLastLexeme);

        String punctuation = sentence instanceof UniversalTextComponent ?
                ((UniversalTextComponent) sentence).getTrailingPunctuation() : "";
        return new UniversalTextComponent(TextType.SENTENCE, punctuation, newLexemes);
    }

    private TextComponent createSwappedLexeme(TextComponent originalLexeme, boolean capitalize) {
        if (originalLexeme.getType() != TextType.LEXEMA) {
            return originalLexeme;
        }

        // Получаем текст лексемы и извлекаем слово + пунктуацию
        String originalText = originalLexeme.getText();
        Pattern pattern = Pattern.compile("^(.*?)(\\p{P}*)$");
        Matcher matcher = pattern.matcher(originalText);

        if (matcher.matches()) {
            String wordPart = matcher.group(1);
            String punctuationPart = matcher.group(2);

            String modifiedWord = capitalize ?
                    utils.capitalizeFirstLetter(wordPart) :
                    utils.lowercaseFirstLetter(wordPart);

            TextComponent newWord = new UniversalTextComponent(TextType.WORD, modifiedWord);
            return new UniversalTextComponent(TextType.LEXEMA, punctuationPart, Arrays.asList(newWord));
        }

        return originalLexeme;
    }

    // Рекурсивный обход для получения всех компонентов заданного типа
    private List<TextComponent> getAllComponentsByType(TextComponent component, TextType targetType) {
        List<TextComponent> result = new ArrayList<>();
        collectComponentsByType(component, targetType, result);
        return result;
    }

    private void collectComponentsByType(TextComponent component, TextType targetType, List<TextComponent> result) {
        if (component.getType() == targetType) {
            result.add(component);
        }

        for (TextComponent child : component.getChildren()) {
            collectComponentsByType(child, targetType, result);
        }
    }
}
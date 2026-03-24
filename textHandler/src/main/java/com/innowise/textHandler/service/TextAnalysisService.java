package com.innowise.textHandler.service;


import com.innowise.textHandler.entity.Impl.Paragraph;
import com.innowise.textHandler.entity.Impl.Sentence;
import com.innowise.textHandler.entity.Impl.Text;
import com.innowise.textHandler.entity.Impl.Word;
import com.innowise.textHandler.entity.TextComponent;
import com.innowise.textHandler.util.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class TextAnalysisService {
    private static final Logger logger = LogManager.getLogger(TextAnalysisService.class);

    // Задача 1: Найти наибольшее количество предложений с одинаковыми словами
    public int findMaxSentencesWithCommonWords(Text text) {
        List<Sentence> sentences = getAllSentences(text);
        Map<Set<String>, Integer> wordSetCounts = new HashMap<>();

        for (Sentence sentence : sentences) {
            Set<String> words = sentence.getChildren().stream()
                    .map(TextComponent::getText)
                    .map(token -> token.toLowerCase().replaceAll("[^a-zа-яё]", ""))
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toSet());

            wordSetCounts.merge(words, 1, Integer::sum);
        }

        return wordSetCounts.values().stream().max(Integer::compareTo).orElse(0);
    }

    // Задача 2: Сортировка предложений по количеству лексем
    public List<Sentence> sortSentencesByTokenCount(Text text) {
        return getAllSentences(text).stream()
                .sorted(Comparator.comparingInt(s -> s.getChildren().size()))
                .collect(Collectors.toList());
    }

    // Задача 3: Поменять первую и последнюю лексему в каждом предложении
    public Text swapFirstLastTokens(Text text) {
        Text result = new Text();

        for (TextComponent paragraphComponent : text.getChildren()) {
            Paragraph originalParagraph = (Paragraph) paragraphComponent;
            Paragraph newParagraph = new Paragraph();

            for (TextComponent sentenceComponent : originalParagraph.getChildren()) {
                Sentence originalSentence = (Sentence) sentenceComponent;
                Sentence swappedSentence = swapFirstLastInSentence(originalSentence);
                newParagraph.addSentence(swappedSentence);
            }

            result.addParagraph(newParagraph);

        }
        return result;
    }

    private Sentence swapFirstLastInSentence(Sentence sentence) {
        List<TextComponent> tokens = sentence.getChildren();
        if (tokens.size() < 2) {
            Sentence copy = new Sentence(sentence.getEndingPunctuation());
            tokens.forEach(copy::addToken);
            return copy;
        }

        Sentence newSentence = new Sentence(sentence.getEndingPunctuation());
        // Меняем местами первый и последний
        String firstWord = tokens.get(0).getText();
        String lastWord = tokens.get(tokens.size() - 1).getText();

        String newFirst = utils.capitalizeFirstLetter(lastWord);
        String newLast = utils.lowercaseFirstLetter(firstWord);

        newSentence.addToken(new Word(newFirst));
        for (int i = 1; i < tokens.size() - 1; i++) {
            newSentence.addToken(tokens.get(i));
        }
        newSentence.addToken(new Word(newLast));
        return newSentence;
    }

    private List<Sentence> getAllSentences(Text text) {
        return text.getChildren().stream()
                .flatMap(p -> p.getChildren().stream())
                .map(Sentence.class::cast)
                .collect(Collectors.toList());
    }

    private List<Sentence> getAllSentences(Paragraph paragraph) {
        return paragraph.getChildren().stream()
                .map(Sentence.class::cast)
                .collect(Collectors.toList());
    }

    private List<Paragraph> getAllParagraphs(Text text) {
        return text.getChildren().stream()
                .map(Paragraph.class::cast)
                .collect(Collectors.toList());
    }

}

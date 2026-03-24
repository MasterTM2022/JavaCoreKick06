package com.innowise.textHandler;

import com.innowise.textHandler.entity.Impl.Sentence;
import com.innowise.textHandler.entity.Impl.Text;
import com.innowise.textHandler.parser.Impl.ParagraphParserImpl;
import com.innowise.textHandler.parser.Impl.SentenceParserImpl;
import com.innowise.textHandler.parser.Impl.TextParserImpl;
import com.innowise.textHandler.parser.TextParser;
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

            // Создание цепочки парсеров
            TextParser textParser = new TextParserImpl();
            ParagraphParserImpl paragraphParser = new ParagraphParserImpl();
            SentenceParserImpl sentenceParser = new SentenceParserImpl();

            textParser.setNext(paragraphParser);
            paragraphParser.setNext(sentenceParser);

            // Парсинг
            Text parsedText = (Text) textParser.parse(text);
            logger.info("Parsed text char count: {}", parsedText.getCharCount());

            // Анализ
            TextAnalysisService service = new TextAnalysisService();

            // Задача 1
            int maxCommon = service.findMaxSentencesWithCommonWords(parsedText);
            logger.info("Max sentences with common words: {}", maxCommon > 1 ? maxCommon : 0);

            // Задача 2
            List<Sentence> sorted = service.sortSentencesByTokenCount(parsedText);
            logger.info("Sorted sentences: \r\n{}", sorted.stream()
                    .map(Sentence::getText)
                    .collect(Collectors.joining("\n")));

            // Задача 3
            Text swapped = service.swapFirstLastTokens(parsedText);
            logger.info("Text after swap: \r\n{}", swapped.getText());

        } catch (IOException e) {
            logger.error("Error reading file", e);
        }
    }
}


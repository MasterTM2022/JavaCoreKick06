package com.innowise.multiapplibrary;

import com.innowise.multiapplibrary.config.ConfigManager;
import com.innowise.multiapplibrary.entity.Book;
import com.innowise.multiapplibrary.entity.Reader;
import com.innowise.multiapplibrary.service.LibraryService;
import com.innowise.multiapplibrary.task.ReaderTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        ConfigManager config = ConfigManager.getInstance();

        List<Book> books = new ArrayList<>();
        int bookCount = config.getInt("books");
        for (int i = 1; i <= bookCount; i++) {
            books.add(new Book(config.getString("book." + i)));
        }

        LibraryService library = new LibraryService(books);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<List<String>>> tasks = new ArrayList<>();

        int readerCount = config.getInt("readers");
        int limit = config.getInt("limit");

        for (int i = 1; i <= readerCount; i++) {
            String name = config.getString("reader." + i);
            Reader reader = new Reader(name, limit);
            tasks.add(new ReaderTask(reader, library, limit));
        }

        try {
            log.info("🚀 Запуск библиотеки...");
            // invokeAll blocks main until all Callables are completed.
            List<Future<List<String>>> futures = executor.invokeAll(tasks);

            log.info("📊 Итоги работы:");
            for (Future<List<String>> future : futures) {
                log.info("📜 {}", future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Ошибка выполнения", e);
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
            log.info("🔚 Приложение завершено.");
        }
    }
}

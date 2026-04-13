package com.innowise.multiapplibrary.task;

import com.innowise.multiapplibrary.entity.Reader;
import com.innowise.multiapplibrary.service.LibraryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ReaderTask implements Callable<List<String>> {
    private static final Logger log = LogManager.getLogger(ReaderTask.class);
    private final Reader reader;
    private final LibraryService service;
    private final Semaphore readerLimit;

    public ReaderTask(Reader reader, LibraryService service, int limit) {
        this.reader = reader;
        this.service = service;
        this.readerLimit = new Semaphore(limit);
    }

    @Override
    public List<String> call() throws Exception {
        List<String> report = new ArrayList<>();
        report.add(String.format("👤 %s начал работу", reader.getName()));

        int attempts = 0;
        final int MAX_ATTEMPTS = 10;

        try {
            while (reader.canBorrowMore() && attempts < MAX_ATTEMPTS) {
                attempts++;
                boolean borrowed = service.borrowBook(reader, readerLimit);

                if (borrowed) {
                    report.add(String.format("✅ Успешно взял книгу (попытка #%d)", attempts));
                } else {
                    report.add(String.format("⏳ Не удалось взять книгу (попытка #%d)", attempts));
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }

            if (reader.canBorrowMore()) {
                report.add(String.format("⚠️ %s не смог набрать лимит за %d попыток", reader.getName(), MAX_ATTEMPTS));
            } else {
                report.add(String.format("✅ %s выполнил норму!", reader.getName()));
                // Reading imitation
                TimeUnit.SECONDS.sleep(1);
            }

            int finalBookCount = reader.getBorrowedBooks().size();

            // Return books (set resources free)
            int returnedCount = service.returnAllBooks(reader, readerLimit);
            report.add(String.format("🔚 %s завершил работу, всего книг: %d",
                    reader.getName(), returnedCount));
        } catch (Exception e) {
            report.add(String.format("❌ Ошибка: %s", e.getMessage()));
        }
        return report;
    }
}

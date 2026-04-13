package com.innowise.multiapplibrary.service;

import com.innowise.multiapplibrary.entity.Book;
import com.innowise.multiapplibrary.entity.Reader;
import com.innowise.multiapplibrary.state.impl.AvailableState;
import com.innowise.multiapplibrary.state.impl.BorrowedState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LibraryService {
    private static final Logger log = LogManager.getLogger(LibraryService.class);
    private final List<Book> books;
    private final ReentrantLock catalogLock = new ReentrantLock();

    public LibraryService(List<Book> books) {
        this.books = books;
    }

    public boolean borrowBook(Reader reader, Semaphore readerLimitSemaphore) {
        catalogLock.lock();
        try {
            for (Book book : books) {
                if (!reader.canBorrowMore()) break;

                // 🔹 Step 1: try to catch semaphore's slot (unblocking)
                if (!readerLimitSemaphore.tryAcquire()) {
                    return false; // Limit is exhausted — there is no point in continuing
                }

                // 🔹 Step 2: try to catch book's lock with timeout
                boolean bookLocked = false;
                try {
                    bookLocked = book.getStateLock().tryLock(100, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    // ✅ Restore state of interruption
                    Thread.currentThread().interrupt();
                    log.warn("Поток {} прерван при ожидании лока книги",
                            Thread.currentThread().getName());
                    // ❗ Important: to take back semaphore's slot due it ia caught!
                    readerLimitSemaphore.release();
                    return false;
                }

                // 🔹 Step 3: If it is not possible to capture the book, we return the slot and move on.
                if (!bookLocked) {
                    readerLimitSemaphore.release(); // ✅ return slot because book is not taken
                    continue; // try next book
                }

                // 🔹 Step 4: There are both slot and book's lock — do main logic
                try {
                    if (book.getState() instanceof AvailableState) {
                        book.setState(BorrowedState.INSTANCE);
                        reader.addBook(book);

                        log.info("📖 {} взял '{}' (лимит: {}/{})",
                                reader.getName(), book.getTitle(),
                                reader.getBorrowedBooks().size(), reader.getLimit());
                        return true; // ✅ Success! Slot and lock are not needed yet
                    }
                    // If book is unavailable  — go through (slot wil be returned in finally block)
                } finally {
                    book.getStateLock().unlock();
                }

                // 🔹 Step 5: If book is not good - return semaphore's slot
                    readerLimitSemaphore.release();
            }
        } finally {
            catalogLock.unlock();
        }
        return false;
    }

    public int returnAllBooks(Reader reader, Semaphore readerLimitSemaphore) {
        int returnedCount = reader.getBorrowedBooks().size();

        for (Book book : reader.getBorrowedBooks()) {
            book.getStateLock().lock();
            try {
                book.setState(AvailableState.INSTANCE);
                log.info("📥 {} вернул '{}'", reader.getName(), book.getTitle());
            } finally {
                book.getStateLock().unlock();
            }
        }
        reader.clearBooks();
        readerLimitSemaphore.release(returnedCount);
        return returnedCount;
    }
}

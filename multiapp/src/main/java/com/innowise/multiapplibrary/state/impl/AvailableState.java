package com.innowise.multiapplibrary.state.impl;

import com.innowise.multiapplibrary.entity.Book;
import com.innowise.multiapplibrary.state.BookState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AvailableState implements BookState {
    private static final Logger log = LogManager.getLogger(AvailableState.class);
    public static final AvailableState INSTANCE = new AvailableState();

    private AvailableState() {
    }

    @Override
    public boolean tryBorrow(Book book, String readerName) {
        book.setState(BorrowedState.INSTANCE);
        log.info("[STATE] {} -> BORROWED by {}", book.getTitle(), readerName);
        return true;
    }

    @Override
    public void returnBook(Book book) {
    } // Не применяется

    @Override
    public String getName() {
        return "AVAILABLE";
    }
}

package com.innowise.multiapplibrary.state.impl;

import com.innowise.multiapplibrary.entity.Book;
import com.innowise.multiapplibrary.state.BookState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BorrowedState implements BookState {
    private static final Logger log = LogManager.getLogger(BorrowedState.class);
    public static final BorrowedState INSTANCE = new BorrowedState();

    private BorrowedState() {
    }

    @Override
    public boolean tryBorrow(Book book, String readerName) {
        log.info("[STATE] {} is BUSY. Cannot borrow.", book.getTitle());
        return false;
    }

    @Override
    public void returnBook(Book book) {
        book.setState(AvailableState.INSTANCE);
        System.out.println("[STATE] " + book.getTitle() + " -> AVAILABLE");
    }

    @Override
    public String getName() {
        return "BORROWED";
    }
}

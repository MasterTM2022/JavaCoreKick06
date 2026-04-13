package com.innowise.multiapplibrary.state.impl;

import com.innowise.multiapplibrary.entity.Book;
import com.innowise.multiapplibrary.state.BookState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReservedState implements BookState {
    private static final Logger log = LogManager.getLogger(ReservedState.class);
    public static final ReservedState INSTANCE = new ReservedState();

    private ReservedState() {
    }

    @Override
    public boolean tryBorrow(Book book, String readerName) {
        log.info("[STATE] {} is RESERVED. Waiting list not implemented.", book.getTitle());
        return false;
    }

    @Override
    public void returnBook(Book book) {
        book.setState(AvailableState.INSTANCE);
    }

    @Override
    public String getName() {
        return "RESERVED";
    }
}

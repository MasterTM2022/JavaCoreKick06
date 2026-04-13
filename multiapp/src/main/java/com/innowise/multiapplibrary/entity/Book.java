package com.innowise.multiapplibrary.entity;

import com.innowise.multiapplibrary.state.BookState;
import com.innowise.multiapplibrary.state.impl.AvailableState;

import java.util.concurrent.locks.ReentrantLock;

public class Book {
    private final String title;
    private BookState state = AvailableState.INSTANCE;
    private final ReentrantLock stateLock = new ReentrantLock();

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public BookState getState() {
        return state;
    }

    // ✅ public getter to access to lock from the outside
    public ReentrantLock getStateLock() {
        return stateLock;
    }

    // ✅ public for changing state by State and Service classes
    public void setState(BookState newState) {
        this.state = newState;
    }

    // Auxiliary methods can be left, but the main logic is now in the Service.
    public boolean tryBorrow(String readerName) {
        stateLock.lock();
        try {
            return state.tryBorrow(this, readerName);
        } finally {
            stateLock.unlock();
        }
    }

    public void returnBook() {
        stateLock.lock();
        try {
            state.returnBook(this);
        } finally {
            stateLock.unlock();
        }
    }
}

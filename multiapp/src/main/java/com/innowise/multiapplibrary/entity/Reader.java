package com.innowise.multiapplibrary.entity;

import java.util.ArrayList;
import java.util.List;

public class Reader {
    private final String name;
    private final int limit;
    private final List<Book> borrowedBooks = new ArrayList<>();

    public Reader(String name, int limit) {
        this.name = name;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public int getLimit() {
        return limit;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean canBorrowMore() {
        return borrowedBooks.size() < limit;
    }

    public void addBook(Book book) {
        borrowedBooks.add(book);
    }

    public void clearBooks() {
        borrowedBooks.clear();
    }
}

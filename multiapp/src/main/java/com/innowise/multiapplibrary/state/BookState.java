package com.innowise.multiapplibrary.state;

import com.innowise.multiapplibrary.entity.Book;

public interface BookState {
    boolean tryBorrow(Book book, String readerName);

    void returnBook(Book book);

    String getName();
}

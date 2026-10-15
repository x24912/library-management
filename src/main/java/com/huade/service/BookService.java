package com.huade.service;

import com.huade.entity.Book;
import com.huade.entity.PageResult;

public interface BookService {
    Book findById(int id);
    int add(Book book);
    int modify(Book book);
    int remove(int id);
    PageResult<Book> search(String keyword, int page, int limit);
}

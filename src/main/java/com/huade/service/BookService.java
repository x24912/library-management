package com.huade.service;

import com.huade.entity.Book;
import com.huade.entity.PageResult;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(int id);
    int add(Book book);
    int modify(Book book);
    int remove(int id);
    PageResult<Book> search(String keyword, int page, int limit);
}

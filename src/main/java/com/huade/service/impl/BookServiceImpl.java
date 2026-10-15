package com.huade.service.impl;

import com.huade.entity.Book;
import com.huade.entity.PageResult;
import com.huade.mapper.BookMapper;
import com.huade.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book findById(int id) {
        return bookMapper.findById(id);
    }

    @Override
    public int add(Book book) {
        return bookMapper.insert(book);
    }

    @Override
    public int modify(Book book) {
        return bookMapper.update(book);
    }

    @Override
    public int remove(int id) {
        return bookMapper.deleteById(id);
    }

    @Override
    public PageResult<Book> search(String keyword, int page, int limit) {
        List<Book> data = bookMapper.search(keyword, (page - 1) * limit, limit);
        int total = bookMapper.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}

package com.huade.service.impl;

import com.huade.dao.BookDao;
import com.huade.dao.impl.BookDaoImpl;
import com.huade.entity.Book;
import com.huade.entity.PageResult;
import com.huade.service.BookService;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Book findById(int id) {
        return bookDao.findById(id);
    }

    @Override
    public int add(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public int modify(Book book) {
        return bookDao.update(book);
    }

    @Override
    public int remove(int id) {
        return bookDao.delete(id);
    }

    @Override
    public PageResult<Book> search(String keyword, int page, int limit) {
        List<Book> data = bookDao.search(keyword, page, limit);
        int total = bookDao.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}

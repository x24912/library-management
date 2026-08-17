package com.huade.service.impl;

import com.huade.dao.BookCategoryDao;
import com.huade.dao.impl.BookCategoryDaoImpl;
import com.huade.entity.BookCategory;
import com.huade.entity.PageResult;
import com.huade.service.BookCategoryService;
import java.util.List;

public class BookCategoryServiceImpl implements BookCategoryService {
    private BookCategoryDao categoryDao = new BookCategoryDaoImpl();

    @Override
    public List<BookCategory> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public BookCategory findById(int id) {
        return categoryDao.findById(id);
    }

    @Override
    public int add(BookCategory category) {
        return categoryDao.insert(category);
    }

    @Override
    public int modify(BookCategory category) {
        return categoryDao.update(category);
    }

    @Override
    public int remove(int id) {
        return categoryDao.delete(id);
    }

    @Override
    public PageResult<BookCategory> search(String keyword, int page, int limit) {
        List<BookCategory> data = categoryDao.search(keyword, page, limit);
        int total = categoryDao.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}

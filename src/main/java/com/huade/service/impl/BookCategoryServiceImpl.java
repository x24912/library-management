package com.huade.service.impl;

import com.huade.entity.BookCategory;
import com.huade.entity.PageResult;
import com.huade.mapper.BookCategoryMapper;
import com.huade.service.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    @Autowired
    private BookCategoryMapper bookCategoryMapper;

    @Override
    public List<BookCategory> findAll() {
        return bookCategoryMapper.findAll();
    }

    @Override
    public BookCategory findById(int id) {
        return bookCategoryMapper.findById(id);
    }

    @Override
    public int add(BookCategory category) {
        return bookCategoryMapper.insert(category);
    }

    @Override
    public int modify(BookCategory category) {
        return bookCategoryMapper.update(category);
    }

    @Override
    public int remove(int id) {
        return bookCategoryMapper.deleteById(id);
    }

    @Override
    public PageResult<BookCategory> search(String keyword, int page, int limit) {
        List<BookCategory> data = bookCategoryMapper.search(keyword, (page - 1) * limit, limit);
        int total = bookCategoryMapper.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}

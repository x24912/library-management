package com.huade.service.impl;

import com.huade.entity.BookReview;
import com.huade.entity.PageResult;
import com.huade.mapper.BookReviewMapper;
import com.huade.service.BookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService {

    @Autowired
    private BookReviewMapper bookReviewMapper;

    @Override
    public List<BookReview> findAll() {
        return bookReviewMapper.findAll();
    }

    @Override
    public BookReview findById(int id) {
        return bookReviewMapper.findById(id);
    }

    @Override
    public int add(BookReview review) {
        return bookReviewMapper.insert(review);
    }

    @Override
    public int modify(BookReview review) {
        return bookReviewMapper.update(review);
    }

    @Override
    public int remove(int id) {
        return bookReviewMapper.deleteById(id);
    }

    @Override
    public PageResult<BookReview> search(String keyword, int page, int limit) {
        List<BookReview> data = bookReviewMapper.search(keyword, (page - 1) * limit, limit);
        int total = bookReviewMapper.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}

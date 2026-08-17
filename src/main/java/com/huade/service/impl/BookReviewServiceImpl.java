package com.huade.service.impl;

import com.huade.dao.BookReviewDao;
import com.huade.dao.impl.BookReviewDaoImpl;
import com.huade.entity.BookReview;
import com.huade.entity.PageResult;
import com.huade.service.BookReviewService;
import java.util.List;

public class BookReviewServiceImpl implements BookReviewService {
    private BookReviewDao reviewDao = new BookReviewDaoImpl();

    @Override
    public List<BookReview> findAll() {
        return reviewDao.findAll();
    }

    @Override
    public BookReview findById(int id) {
        return reviewDao.findById(id);
    }

    @Override
    public int add(BookReview review) {
        return reviewDao.insert(review);
    }

    @Override
    public int modify(BookReview review) {
        return reviewDao.update(review);
    }

    @Override
    public int remove(int id) {
        return reviewDao.delete(id);
    }

    @Override
    public PageResult<BookReview> search(String keyword, int page, int limit) {
        List<BookReview> data = reviewDao.search(keyword, page, limit);
        int total = reviewDao.count(keyword);
        return new PageResult<>(data, page, limit, total);
    }
}
